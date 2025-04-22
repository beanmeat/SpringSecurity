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
     * 张琪用户 拥有以下资源
     *
     * 线索管理
     * 线索管理
     * 线索管理-列表
     * 线索管理-录入
     * 线索管理-编辑
     * 线索管理-查看
     * 系统管理-列表
     * 线索管理-导入
     */
    @PreAuthorize(value = "hasRole('saler')")
    @GetMapping("/api/clue/menu")
    public String clueMenu() {
        return "clueMenu";
    }

    @PreAuthorize(value = "hasRole('saler')")
    @GetMapping("/api/clue/menu/child")
    public String clueMenuChild(){
        return "clueMenuChild";
    }

    @PreAuthorize(value = "hasRole('saler')")
    @GetMapping("/api/clue/list")
    public String clueList(){
        return "clueList";
    }

    @PreAuthorize(value = "hasRole('saler')")
    @GetMapping("/api/clue/input")
    public String clueInput(){
        return "clueInput";
    }

    @PreAuthorize(value = "hasRole('saler')")
    @GetMapping("/api/clue/edit")
    public String clueEdit(){
        return "clueEdit";
    }

    @PreAuthorize(value = "hasRole('saler')")
    @GetMapping("/api/clue/view")
    public String clueView(){
        return "clueView";
    }

    @PreAuthorize(value = "hasRole('admin')")
    @GetMapping("/api/clue/del")
    public String clueDel(){
        return "clueDel";
    }

    @PreAuthorize(value = "hasAnyRole('admin','manager')")
    @GetMapping("/api/clue/export")
    public String clueExport(){
        return "clueExport";
    }

    @GetMapping("/api/clue/index")
    public String index(){
        return "index";
    }
}
