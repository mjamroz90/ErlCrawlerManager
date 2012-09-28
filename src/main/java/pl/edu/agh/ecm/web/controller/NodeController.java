package pl.edu.agh.ecm.web.controller;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.agh.ecm.crawler.CrawlerConnector;
import pl.edu.agh.ecm.domain.Node;
import pl.edu.agh.ecm.service.NodeService;
import pl.edu.agh.ecm.web.form.Message;
import pl.edu.agh.ecm.web.form.NodeExt;
import pl.edu.agh.ecm.web.form.NodeGrid;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 26.09.12
 * Time: 19:08
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping("/nodes")
public class NodeController {

    @Autowired
    CrawlerConnector crawlerConnector;

    @Autowired
    NodeService nodeService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel){

        Node newNode = new Node();
        uiModel.addAttribute("newNode",newNode);
        return "nodes/list";
    }

    @RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public NodeGrid listGrid(@RequestParam(value = "page", required = false)Integer page,
                           @RequestParam(value = "rows",required = false)Integer rows,
                           @RequestParam(value = "sidx", required = false)String sortBy,
                           @RequestParam(value = "sord", required = false)String order)
    {

        Sort sort = null;
        String orderBy = sortBy;
        if (orderBy != null && orderBy.equals("name")){
            orderBy = "name";
        }

        if (orderBy != null && order != null) {
            if (order.equals("desc")) {
                sort = new Sort(Sort.Direction.DESC, orderBy);
            } else
                sort = new Sort(Sort.Direction.ASC, orderBy);
        }

        PageRequest pageRequest = null;
        if (sort != null) {
            pageRequest = new PageRequest(page - 1, rows, sort);
        } else {
            pageRequest = new PageRequest(page - 1, rows);
        }

        Page<Node> nodePage = nodeService.findByPage(pageRequest);
        NodeGrid nodeGrid = new NodeGrid();
        nodeGrid.setCurrentPage(nodePage.getNumber()+1);
        nodeGrid.setTotalPages(nodePage.getTotalPages());
        nodeGrid.setTotalRecords(nodePage.getTotalElements());
        List<Node> nodes = Lists.newArrayList(nodePage.iterator());
        nodeGrid.setNodeData(checkAccessiblity(nodes));

        return nodeGrid;
    }

    @RequestMapping(params = "addNode", method = RequestMethod.POST)
    public String addNode(@ModelAttribute("newNode") @Valid Node newNode,BindingResult bindingResult,Model uiModel,
                          RedirectAttributes redirectAttributes,Locale locale)
    {
        validateNode(newNode.getName(),newNode.getAddress(),bindingResult);
        if (bindingResult.hasErrors()){
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("label_node_add_failure",new Object[]{},locale)));
            uiModel.addAttribute("newNode",newNode);
            return "nodes/list";
        }
        //uiModel.asMap().clear();
        //redirectAttributes.addFlashAttribute("message")

        uiModel.addAttribute("message", new Message("success",
                messageSource.getMessage("label_node_add_success", new Object[]{},locale)));
        nodeService.save(newNode);
        uiModel.addAttribute("newNode", new Node());

        return "nodes/list";
    }

    private List<NodeExt> checkAccessiblity(List<Node> nodes){

        List<NodeExt> nodeExtList = new ArrayList<NodeExt>();
        for (Node node : nodes){
            NodeExt nodeExt = new NodeExt(node);
            nodeExt.setAccessible(crawlerConnector.pingNode(node.toString()));
            nodeExtList.add(nodeExt);
        }

        return nodeExtList;
    }

    private void validateNode(String nodeName,String nodeAddress, Errors errors){
        Node node;
        if ((node = nodeService.findByNameAndAddress(nodeName,nodeAddress)) != null){
            errors.rejectValue("name", "label_node_duplicate_name_address",new String[]{nodeName,nodeAddress},null);
        }
    }
}
