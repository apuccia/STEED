package it.unipi.trustgraphmanager.controllers;

import it.unipi.trustgraphmanager.dtos.AvgTopFiveTrustPathsDTO;
import it.unipi.trustgraphmanager.dtos.MaxTrustPathDTO;
import it.unipi.trustgraphmanager.dtos.MinMaxTrustPathDTO;
import it.unipi.trustgraphmanager.dtos.TrustQueryDTO;
import it.unipi.trustgraphmanager.exceptions.ControllerException;
import it.unipi.trustgraphmanager.exceptions.ServiceException;
import it.unipi.trustgraphmanager.services.TrustQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/query")
public class TrustQueryController {

    @Autowired
    TrustQueryService trustQueryService;

    @PostMapping(path = "/maxtrustpath", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public MaxTrustPathDTO maxTrustPathQuery(@RequestBody final TrustQueryDTO trustQueryDto)
            throws ControllerException {
        try {
            return trustQueryService.maxTrustPathQuery(trustQueryDto);
        } catch (final ServiceException e) {
            throw new ControllerException(e, e.getErrorMessage());
        }
    }

    @PostMapping(path = "/avgtopfive", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public AvgTopFiveTrustPathsDTO avgTopFiveTrustPaths(@RequestBody final TrustQueryDTO trustQueryDto)
            throws ControllerException {
        try {
            return trustQueryService.avgTopFiveTrustPathsQuery(trustQueryDto);
        } catch (final ServiceException e) {
            throw new ControllerException(e, e.getErrorMessage());
        }
    }

    @PostMapping(path = "/minmaxtrustpath", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public MinMaxTrustPathDTO minMaxTrustPaths(@RequestBody final TrustQueryDTO trustQueryDto)
            throws ControllerException {
        try {
            return trustQueryService.minMaxTrustPathsQuery(trustQueryDto);
        } catch (final ServiceException e) {
            throw new ControllerException(e, e.getErrorMessage());
        }
    }
}
