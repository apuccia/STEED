package it.unipi.trustgraphmanager.controllers;

import it.unipi.trustgraphmanager.dtos.NodeDTO;
import it.unipi.trustgraphmanager.dtos.NodeTransactionResultDTO;
import it.unipi.trustgraphmanager.exceptions.ControllerException;
import it.unipi.trustgraphmanager.exceptions.ServiceException;
import it.unipi.trustgraphmanager.services.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nodes")
public class NodeController {

    @Autowired
    private NodeService nodeService;

    @PostMapping(path = "/issuer", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public NodeTransactionResultDTO insertIssuerNode(@RequestBody final NodeDTO issuerNodeDTO)
            throws ControllerException {
        try {
            return nodeService.insertIssuerNode(issuerNodeDTO);
        } catch (ServiceException e) {
            throw new ControllerException(e, e.getErrorMessage());
        }
    }

    @PostMapping(path = "/entrypoint/issuer", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public NodeTransactionResultDTO insertIssuerNodeInEntrypointLayer(@RequestBody final NodeDTO issuerNodeDTO)
            throws ControllerException {
        try {
            return nodeService.insertIssuerNodeInEntrypointLayer(issuerNodeDTO);
        } catch (ServiceException e) {
            throw new ControllerException(e, e.getErrorMessage());
        }
    }

    @DeleteMapping(path = "/issuer", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public NodeTransactionResultDTO deactivateIssuerNode(@RequestBody NodeDTO issuerNodeDTO)
            throws ControllerException {
        try {
            return nodeService.deactivateIssuerNode(issuerNodeDTO);
        } catch (ServiceException e) {
            throw new ControllerException(e, e.getErrorMessage());
        }
    }

    @DeleteMapping(path = "/entrypoint/issuer", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public NodeTransactionResultDTO deactivateIssuerNodeInEntrypointLayer(@RequestBody NodeDTO issuerNodeDTO)
            throws ControllerException {
        try {
            return nodeService.deactivateIssuerNodeInEntrypointLayer(issuerNodeDTO);
        } catch (ServiceException e) {
            throw new ControllerException(e, e.getErrorMessage());
        }
    }

    @PostMapping(path = "/context", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public NodeTransactionResultDTO insertContextNode(@RequestBody final NodeDTO contextNodeDTO)
            throws ControllerException {
        try {
            return nodeService.insertContextNode(contextNodeDTO);
        } catch (final ServiceException e) {
            throw new ControllerException(e, e.getErrorMessage());
        }
    }

    @PostMapping(path = "/entrypoint/context", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public NodeTransactionResultDTO insertContextNodeInEntrypointLayer(@RequestBody NodeDTO contextNodeDTO)
            throws ControllerException {
        try {
            return nodeService.insertContextNodeInEntrypointLayer(contextNodeDTO);
        } catch (final ServiceException e) {
            throw new ControllerException(e, e.getErrorMessage());
        }
    }

    @DeleteMapping(path = "/context", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public NodeTransactionResultDTO deactivateContextNode(@RequestBody final NodeDTO contextNodeDTO)
            throws ControllerException {
        try {
            return nodeService.deactivateContextNode(contextNodeDTO);
        } catch (ServiceException e) {
            throw new ControllerException(e, e.getErrorMessage());
        }
    }

    @DeleteMapping(path = "/entrypoint/context", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public NodeTransactionResultDTO deactivateContextNodeInEntrypointLayer(@RequestBody final NodeDTO contextNodeDTO)
            throws ControllerException {
        try {
            return nodeService.deactivateContextNodeInEntrypointLayer(contextNodeDTO);
        } catch (ServiceException e) {
            throw new ControllerException(e, e.getErrorMessage());
        }
    }
}
