package it.unipi.trustgraphmanager.controllers;

import it.unipi.trustgraphmanager.dtos.VerifyEdgeDTO;
import it.unipi.trustgraphmanager.dtos.VerifyEdgeTransactionResultDTO;
import it.unipi.trustgraphmanager.exceptions.ControllerException;
import it.unipi.trustgraphmanager.exceptions.ServiceException;
import it.unipi.trustgraphmanager.services.PathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/path")
public class PathController {

    @Autowired
    PathService pathService;

    @PostMapping(path = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public VerifyEdgeTransactionResultDTO verifyPath(@RequestBody List<VerifyEdgeDTO> path)
            throws ControllerException {
        try {
            return pathService.verifyPath(path);
        } catch (ServiceException e) {
            throw new ControllerException(e, e.getErrorMessage());
        }
    }
}
