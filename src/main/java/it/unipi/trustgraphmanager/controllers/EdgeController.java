package it.unipi.trustgraphmanager.controllers;

import it.unipi.trustgraphmanager.dtos.EdgeDTO;
import it.unipi.trustgraphmanager.dtos.EdgeTransactionResultDTO;
import it.unipi.trustgraphmanager.exceptions.ControllerException;
import it.unipi.trustgraphmanager.exceptions.ServiceException;
import it.unipi.trustgraphmanager.services.EdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/edges")
public class EdgeController {

    @Autowired
    private EdgeService edgeService;

    @PostMapping(path = "/issuer", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public EdgeTransactionResultDTO insertIssuerEdge(@RequestBody final EdgeDTO edge) throws ControllerException {
        try {
            return edgeService.insertIssuerEdge(edge);
        } catch (final ServiceException e) {
            throw new ControllerException(e, e.getErrorMessage());
        }
    }

    @PutMapping(path = "/issuer", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public EdgeTransactionResultDTO modifyIssuerEdgeWeight(@RequestBody final EdgeDTO edge) throws ControllerException {
        try {
            return edgeService.modifyIssuerEdgeWeight(edge);
        } catch (final ServiceException e) {
            throw new ControllerException(e, e.getErrorMessage());
        }
    }

    @PostMapping(path = "/context", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public EdgeTransactionResultDTO insertContextEdge(@RequestBody final EdgeDTO edge) throws ControllerException {
        try {
            return edgeService.insertContextEdge(edge);
        } catch (final ServiceException e) {
            throw new ControllerException(e, e.getErrorMessage());
        }
    }

    @PutMapping(path = "/context", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public EdgeTransactionResultDTO modifyContextEdgeWeight(@RequestBody final EdgeDTO edge)
            throws ControllerException {
        try {
            return edgeService.modifyContextEdgeWeight(edge);
        } catch (final ServiceException e) {
            throw new ControllerException(e, e.getErrorMessage());
        }
    }

    @PostMapping(path = "/entrypoint", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public EdgeTransactionResultDTO insertEntrypointEdge(@RequestBody final EdgeDTO edge) throws ControllerException {
        try {
            return edgeService.insertEntrypointEdge(edge);
        } catch (final ServiceException e) {
            throw new ControllerException(e, e.getErrorMessage());
        }
    }

    @PutMapping(path = "/entrypoint", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public EdgeTransactionResultDTO modifyEntrypointEdgeWeight(@RequestBody final EdgeDTO edge)
            throws ControllerException {
        try {
            return edgeService.modifyEntrypointEdgeWeight(edge);
        } catch (final ServiceException e) {
            throw new ControllerException(e, e.getErrorMessage());
        }
    }
}
