package com.reddot.utilityservice.agentlocator.controller;

import com.reddot.utilityservice.agentlocator.dto.AgentDto;
import com.reddot.utilityservice.agentlocator.dto.ConfigurationDto;
import com.reddot.utilityservice.agentlocator.service.AgentLocatorService;
import com.reddot.utilityservice.agentlocator.service.ConfigurationService;
import com.reddot.utilityservice.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/agent-locator")
public class AgentLocatorController {

    private final AgentLocatorService agentLocatorService;
    private final ConfigurationService configurationService;

    public AgentLocatorController(AgentLocatorService agentLocatorService, ConfigurationService configurationService) {
        this.agentLocatorService = agentLocatorService;
        this.configurationService = configurationService;
    }

    @PostMapping("/update-agent-location")
    public ResponseEntity<Object> updateAgentLocation(@RequestBody AgentDto agentDto, HttpServletRequest request){
        return new ResponseEntity<>(agentLocatorService.saveOrUpdateAgentLocation(agentDto, request.getHeader("SECRET_KEY")), Util.getHeaders(), HttpStatus.OK);
    }

    @GetMapping("/get-nearest-agent")
    public ResponseEntity<Object> getAgentLocationByLatLong(@RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude, HttpServletRequest request){
//        Return nearest agent based on latitude and longitude

//        return new ResponseEntity<>(agentLocatorService.getNearestAgent(latitude, longitude, request.getHeader("SECRET_KEY")), Util.getHeaders(), HttpStatus.OK);
//        Return all agent from db;
        return new ResponseEntity<>(agentLocatorService.allAgentFromDB(request.getHeader("SECRET_KEY")), Util.getHeaders(), HttpStatus.OK);
    }

    @PostMapping("/new-configuration")
    public ResponseEntity<Object> saveAgentConfiguration(@RequestBody ConfigurationDto configuration, HttpServletRequest request){
        return new ResponseEntity<>(configurationService.save(configuration, request.getHeader("SECRET_KEY")), Util.getHeaders(), HttpStatus.OK);
    }

    @GetMapping("/get-configuration/{key}")
    public ResponseEntity<Object> getAgentConfigurationByKey(@PathVariable("key") String key, HttpServletRequest request){
        return new ResponseEntity<>(configurationService.getConfigurationByKey(key), Util.getHeaders(), HttpStatus.OK);
    }

    @GetMapping("/get-configurations")
    public ResponseEntity<Object> getAgentConfigurations(HttpServletRequest request){
        return new ResponseEntity<>(configurationService.getConfigurations(request.getHeader("SECRET_KEY")), Util.getHeaders(), HttpStatus.OK);
    }

}
