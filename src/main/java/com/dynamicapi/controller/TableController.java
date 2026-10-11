package com.dynamicapi.controller;

import com.dynamicapi.service.DatabaseMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/tables")
public class TableController {

    @Autowired
    private DatabaseMetadataService metadataService;

    // هذه الدالة ستعيد أسماء الجداول على شكل JSON
    @GetMapping
    public List<String> getTables() {
        return metadataService.getAllTableNames();
    }
    // مسار جديد لجلب تفاصيل أعمدة جدول محدد
    @GetMapping("/{tableName}/columns")
    public List<com.dynamicapi.model.ColumnMetadata> getTableColumns(
            @org.springframework.web.bind.annotation.PathVariable String tableName) {
        return metadataService.getTableColumns(tableName);
    }
}