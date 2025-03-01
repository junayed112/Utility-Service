package com.reddot.utilityservice.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.TeeOutputStream;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Slf4j
@Component
public class HttpLoggingFilter implements Filter {

    final String DEFAULT_LOCALE = "en";
    String[] AVAILABLE_LOCALES = {"en", "bn"};
    private List<String> getSevletRequestParts(ServletRequest request) {
        String[] splitedParts = ((HttpServletRequest) request).getServletPath().split("/");
        List<String> result = new ArrayList<String>();

        for (String sp : splitedParts) {
            if (sp.trim().length() > 0)
                result.add(sp);
        }
        return result;
    }

    private Locale getLocaleFromRequestParts(List<String> parts) {
        if (parts.size() > 0) {
            for (String lang : AVAILABLE_LOCALES) {
                if (lang.equals(parts.get(0))) {
                    return new Locale(lang);
                }
            }
        }

        return null;
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) {
        //Language Locale Portion
        List<String> requestParts = this.getSevletRequestParts(request);
        Locale locale = this.getLocaleFromRequestParts(requestParts);
        if (locale != null) {
            request.setAttribute(HttpLoggingFilter.class.getName() + ".LOCALE", locale);
        } else {
            request.setAttribute(HttpLoggingFilter.class.getName() + ".LOCALE", new Locale(DEFAULT_LOCALE));
        }

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        try {
            String currentCorrId = httpServletRequest.getHeader(RequestCorrelation.CORRELATION_ID_HEADER_NAME);
            if (currentCorrId == null) {
                currentCorrId = UUID.randomUUID().toString();
            }

            MDC.put(RequestCorrelation.CORRELATION_ID, currentCorrId);
            RequestCorrelation.setId(currentCorrId);

            long startTime = System.currentTimeMillis();
            Map<String, String> requestMap = this
                    .getTypesafeRequestMap(httpServletRequest);
            BufferedRequestWrapper bufferedRequest = new BufferedRequestWrapper(
                    httpServletRequest);
            BufferedResponseWrapper bufferedResponse = new BufferedResponseWrapper(
                    httpServletResponse);

            final StringBuilder logMessage = new StringBuilder(
                    "REST Request - ").append("[HTTP METHOD:")
                    .append(httpServletRequest.getMethod())
                    .append("] [PATH INFO:")
                    .append(httpServletRequest.getServletPath())
                    .append("] [REQUEST PARAMETERS:").append(requestMap)
                    .append("] [REQUEST BODY:")
                    .append(bufferedRequest.getRequestBody())
                    .append("] [REMOTE ADDRESS:")
                    .append(httpServletRequest.getRemoteAddr()).append("]");

            if (httpServletRequest.getServletPath().startsWith("/actuator") || httpServletRequest.getServletPath().contains("/ekyc_notification")) {
                log.trace(logMessage.toString());
            } else {
                log.debug(logMessage.toString());
            }

            logMessage.delete(0, logMessage.length());

            chain.doFilter(bufferedRequest, bufferedResponse);
            long elapsed = System.currentTimeMillis() - startTime;
            logMessage.append(" [RESPONSE:")
                    .append(bufferedResponse.getContent()).append("]");
            logMessage.append(" [REQUEST TIME:")
                    .append(new Date(startTime)).append("]");
            logMessage.append(" [ELAPSED TIME:")
                    .append(elapsed).append("ms").append("]");

            if (httpServletRequest.getServletPath().startsWith("/actuator") || httpServletRequest.getServletPath().contains("/ekyc_notification")) {
                log.trace(logMessage.toString());
            } else {
                log.debug(logMessage.toString());
            }
        } catch (Throwable a) {
            log.error(a.getMessage());
        } finally {
            MDC.remove("CorrelationId");
        }
    }

    private Map<String, String> getTypesafeRequestMap(HttpServletRequest request) {
        Map<String, String> typesafeRequestMap = new HashMap<String, String>();
        Enumeration<?> requestParamNames = request.getParameterNames();
        while (requestParamNames.hasMoreElements()) {
            String requestParamName = (String) requestParamNames.nextElement();
            String requestParamValue;
            if (requestParamName.equalsIgnoreCase("password")) {
                requestParamValue = "********";
            } else {
                requestParamValue = request.getParameter(requestParamName);
            }
            typesafeRequestMap.put(requestParamName, requestParamValue);
        }
        return typesafeRequestMap;
    }

    @Override
    public void destroy() {
    }

    private static final class BufferedRequestWrapper extends
            HttpServletRequestWrapper {

        private ByteArrayInputStream bais = null;
        private ByteArrayOutputStream baos = null;
        private BufferedServletInputStream bsis = null;
        private byte[] buffer = null;

        public BufferedRequestWrapper(HttpServletRequest req)
                throws IOException {
            super(req);
            // Read InputStream and store its content in a buffer.
            InputStream is = req.getInputStream();
            this.baos = new ByteArrayOutputStream();
            byte buf[] = new byte[1024];
            int read;
            while ((read = is.read(buf)) > 0) {
                this.baos.write(buf, 0, read);
            }
            this.buffer = this.baos.toByteArray();
        }

        @Override
        public ServletInputStream getInputStream() {
            this.bais = new ByteArrayInputStream(this.buffer);
            this.bsis = new BufferedServletInputStream(this.bais);
            return this.bsis;
        }

        String getRequestBody() throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    this.getInputStream()));
            String line = null;
            StringBuilder inputBuffer = new StringBuilder();
            do {
                line = reader.readLine();
                if (null != line) {
                    inputBuffer.append(line.trim());
                }
            } while (line != null);
            reader.close();
            return inputBuffer.toString().trim();
        }

    }

    private static final class BufferedServletInputStream extends
            ServletInputStream {

        private ByteArrayInputStream bais;

        public BufferedServletInputStream(ByteArrayInputStream bais) {
            this.bais = bais;
        }

        @Override
        public int available() {
            return this.bais.available();
        }

        @Override
        public int read() {
            return this.bais.read();
        }

        @Override
        public int read(byte[] buf, int off, int len) {
            return this.bais.read(buf, off, len);
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }
    }

    public class TeeServletOutputStream extends ServletOutputStream {

        private final TeeOutputStream targetStream;

        public TeeServletOutputStream(OutputStream one, OutputStream two) {
            targetStream = new TeeOutputStream(one, two);
        }

        @Override
        public void write(int arg0) throws IOException {
            this.targetStream.write(arg0);
        }

        public void flush() throws IOException {
            super.flush();
            this.targetStream.flush();
        }

        public void close() throws IOException {
            super.close();
            this.targetStream.close();
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }
    }

    public class BufferedResponseWrapper implements HttpServletResponse {

        HttpServletResponse original;
        TeeServletOutputStream tee;
        ByteArrayOutputStream bos;

        public BufferedResponseWrapper(HttpServletResponse response) {
            original = response;
        }

        public String getContent() {
            return bos.toString();
        }

        public PrintWriter getWriter() throws IOException {
            return original.getWriter();
        }

        public ServletOutputStream getOutputStream() throws IOException {
            if (tee == null) {
                bos = new ByteArrayOutputStream();
                tee = new TeeServletOutputStream(original.getOutputStream(),
                        bos);
            }
            return tee;

        }

        @Override
        public String getCharacterEncoding() {
            return original.getCharacterEncoding();
        }

        @Override
        public void setCharacterEncoding(String charset) {
            original.setCharacterEncoding(charset);
        }

        @Override
        public String getContentType() {
            return original.getContentType();
        }

        @Override
        public void setContentType(String type) {
            original.setContentType(type);
        }

        @Override
        public void setContentLength(int len) {
            original.setContentLength(len);
        }

        @Override
        public void setContentLengthLong(long l) {
            original.setContentLengthLong(l);
        }

        @Override
        public int getBufferSize() {
            return original.getBufferSize();
        }

        @Override
        public void setBufferSize(int size) {
            original.setBufferSize(size);
        }

        @Override
        public void flushBuffer() throws IOException {
            tee.flush();
        }

        @Override
        public void resetBuffer() {
            original.resetBuffer();
        }

        @Override
        public boolean isCommitted() {
            return original.isCommitted();
        }

        @Override
        public void reset() {
            original.reset();
        }

        @Override
        public Locale getLocale() {
            return original.getLocale();
        }

        @Override
        public void setLocale(Locale loc) {
            original.setLocale(loc);
        }

        @Override
        public void addCookie(Cookie cookie) {
            original.addCookie(cookie);
        }

        @Override
        public boolean containsHeader(String name) {
            return original.containsHeader(name);
        }

        @Override
        public String encodeURL(String url) {
            return original.encodeURL(url);
        }

        @Override
        public String encodeRedirectURL(String url) {
            return original.encodeRedirectURL(url);
        }

        @SuppressWarnings("deprecation")
        @Override
        public String encodeUrl(String url) {
            return original.encodeUrl(url);
        }

        @SuppressWarnings("deprecation")
        @Override
        public String encodeRedirectUrl(String url) {
            return original.encodeRedirectUrl(url);
        }

        @Override
        public void sendError(int sc, String msg) throws IOException {
            original.sendError(sc, msg);
        }

        @Override
        public void sendError(int sc) throws IOException {
            original.sendError(sc);
        }

        @Override
        public void sendRedirect(String location) throws IOException {
            original.sendRedirect(location);
        }

        @Override
        public void setDateHeader(String name, long date) {
            original.setDateHeader(name, date);
        }

        @Override
        public void addDateHeader(String name, long date) {
            original.addDateHeader(name, date);
        }

        @Override
        public void setHeader(String name, String value) {
            original.setHeader(name, value);
        }

        @Override
        public void addHeader(String name, String value) {
            original.addHeader(name, value);
        }

        @Override
        public void setIntHeader(String name, int value) {
            original.setIntHeader(name, value);
        }

        @Override
        public void addIntHeader(String name, int value) {
            original.addIntHeader(name, value);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void setStatus(int sc, String sm) {
            original.setStatus(sc, sm);
        }

        @Override
        public String getHeader(String arg0) {
            return original.getHeader(arg0);
        }

        @Override
        public Collection<String> getHeaderNames() {
            return original.getHeaderNames();
        }

        @Override
        public Collection<String> getHeaders(String arg0) {
            return original.getHeaders(arg0);
        }

        @Override
        public int getStatus() {
            return original.getStatus();
        }

        @Override
        public void setStatus(int sc) {
            original.setStatus(sc);
        }

    }
}