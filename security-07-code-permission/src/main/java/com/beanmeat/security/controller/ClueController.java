package com.beanmeat.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tchstart
 * @data 2025-04-22
 */
@RestController
public class ClueController {
    /**
     * 张琪用户 拥有以下权限
     *
     * 线索管理-列表 clue:list
     * 线索管理-录入 clue:add
     * 线索管理-编辑 clue:edit
     * 线索管理-查看 clue:view
     * 线索管理-导入 clue:import
     */
    @PreAuthorize(value = "hasAuthority('clue:list')")
    @GetMapping("/api/clue/list")
    public String clueList(){
        return "clueList";
    }

    @PreAuthorize(value = "hasAuthority('clue:input')")
    @GetMapping("/api/clue/input")
    public String clueInput(){
        return "clueInput";
    }

    @PreAuthorize(value = "hasAuthority('clue:edit')")
    @GetMapping("/api/clue/edit")
    public String clueEdit(){
        return "clueEdit";
    }

    @PreAuthorize(value = "hasAnyAuthority('clue:del')")
    @GetMapping("/api/clue/del")
    public String clueDel(){
        return "clueDel";
    }

    @PreAuthorize(value = "hasAnyAuthority('clue:import')")
    @GetMapping("/api/clue/import")
    public String clueImport(){
        return "import";
    }

    @GetMapping("/api/clue/index")
    public String index(){
        return "index";
    }
}
