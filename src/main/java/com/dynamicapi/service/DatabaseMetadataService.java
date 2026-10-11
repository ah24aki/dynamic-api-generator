package com.dynamicapi.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dynamicapi.model.ColumnMetadata; // تم استيراد الكلاس هنا

@Service
public class DatabaseMetadataService {

    @Autowired
    private DataSource dataSource;

    // دالة لجلب أسماء جميع الجداول من قاعدة البيانات
    public List<String> getAllTableNames() {
        List<String> tableNames = new ArrayList<>();
        
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            
            String[] types = {"TABLE"};
            ResultSet tables = metaData.getTables(null, "public", "%", types);
            
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                tableNames.add(tableName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return tableNames;
    }
    
    // دالة جديدة لجلب أسماء الأعمدة وأنواع بياناتها لجدول محدد
    public List<ColumnMetadata> getTableColumns(String tableName) {
        List<ColumnMetadata> columns = new ArrayList<>();
        
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            
            // نجلب بيانات الأعمدة للجدول المطلوب
            ResultSet rs = metaData.getColumns(null, "public", tableName, null);
            
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String dataType = rs.getString("TYPE_NAME");
                
                columns.add(new ColumnMetadata(columnName, dataType));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return columns;
    }
}