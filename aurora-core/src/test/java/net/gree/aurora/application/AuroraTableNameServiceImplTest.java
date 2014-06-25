package net.gree.aurora.application;

import net.gree.aurora.domain.tablename.AbstractTableNameResolver;
import net.gree.aurora.domain.tablename.TableName;
import net.gree.aurora.domain.tablename.TableNameFactory;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AuroraTableNameServiceImplTest {

    @Test
    public void test_バリデーションなし() {
        AuroraTableNameService<Integer> atns = AuroraTableNameServiceFactory.create(new AbstractTableNameResolver<Integer>("user") {
            @Override
            protected String getSuffixName(Integer hint) {
                return Integer.toString(hint % 2);
            }
        });
        TableName tableName1 = atns.resolveTableNameByHint(0).get();
        assertThat(tableName1.getName(), is("user0"));
        TableName tableName2 = atns.resolveTableNameByHint(1).get();
        assertThat(tableName2.getName(), is("user1"));
        TableName tableName3 = atns.resolveTableNameByHint(2).get();
        assertThat(tableName3.getName(), is("user0"));
    }

    @Test
    public void test_バリデーションあり_正規表現なし() {
        HashSet<TableName> tableNames = new HashSet<>();
        tableNames.add(TableNameFactory.create("user0", false));
        tableNames.add(TableNameFactory.create("user1", false));
        AuroraTableNameService<Integer> atns = AuroraTableNameServiceFactory.create(new AbstractTableNameResolver<Integer>("user") {
            @Override
            protected String getSuffixName(Integer hint) {
                return Integer.toString(hint % 2);
            }
        }, tableNames);
        TableName tableName1 = atns.resolveTableNameByHint(0).get();
        assertThat(tableName1.getName(), is("user0"));
        TableName tableName2 = atns.resolveTableNameByHint(1).get();
        assertThat(tableName2.getName(), is("user1"));
        TableName tableName3 = atns.resolveTableNameByHint(2).get();
        assertThat(tableName3.getName(), is("user0"));
    }
    
    @Test
    public void test_バリデーションあり_正規表現あり() {
        HashSet<TableName> tableNames = new HashSet<>();
        tableNames.add(TableNameFactory.create("user[0-1]", true));
        AuroraTableNameService<Integer> atns = AuroraTableNameServiceFactory.create(new AbstractTableNameResolver<Integer>("user") {
            @Override
            protected String getSuffixName(Integer hint) {
                return Integer.toString(hint % 2);
            }
        }, tableNames);
        TableName tableName1 = atns.resolveTableNameByHint(0).get();
        assertThat(tableName1.getName(), is("user0"));
        TableName tableName2 = atns.resolveTableNameByHint(1).get();
        assertThat(tableName2.getName(), is("user1"));
        TableName tableName3 = atns.resolveTableNameByHint(2).get();
        assertThat(tableName3.getName(), is("user0"));
    }

    @Test
    public void test_設定ファイル() {
        AuroraTableNameService<Integer> atns = AuroraTableNameServiceFactory.create(new AbstractTableNameResolver<Integer>("user") {
            @Override
            protected String getSuffixName(Integer hint) {
                return Integer.toString(hint % 2);
            }
        }, new File("./conf/application.conf"));
        TableName tableName1 = atns.resolveTableNameByHint(0).get();
        assertThat(tableName1.getName(), is("user0"));
        TableName tableName2 = atns.resolveTableNameByHint(1).get();
        assertThat(tableName2.getName(), is("user1"));
        TableName tableName3 = atns.resolveTableNameByHint(2).get();
        assertThat(tableName3.getName(), is("user0"));
    }
}
