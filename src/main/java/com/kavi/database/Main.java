package com.kavi.database;

import com.kavi.database.engine.datastore.InMemoryDataStore;
import com.kavi.database.engine.indexer.SelfBalancingTree;
import com.kavi.database.engine.indexer.Tree;
import com.kavi.database.engine.metadata.Column;
import com.kavi.database.engine.metadata.Schema;
import com.kavi.database.engine.metadata.Table;
import com.kavi.database.engine.query.Criteria;
import com.kavi.database.engine.validator.Validator;
import com.kavi.database.engine.validator.ValidatorImpl;
import com.kavi.database.importer.DataImporter;
import com.kavi.database.importer.FileDataImporterImpl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public class Main {

    public static void main(String args[]) throws Exception {
        if (args.length != 2 || args[0].trim().isEmpty() || args[1].trim().isEmpty()) {
            System.out.println("Wrong Usage. \n \n Syntax : execute.sh \" <select (*|ID|Salary|Name) " +
                    "[Where (*|ID|Salary|Name) (!=|=|<|>|<=|>=) literal [Order by (ID|Salary|Name) [ASC|DESC]]]> \" " +
                    "<file_path_which_has_the_load_file>  \n\n Note: All the spaces are mandatory and the syntax is case sensitive");
            System.exit(0);
        }

        Schema schema = new Schema();
        schema.setName("MyBase");

        Table employee = new Table();
        employee.setName("Employee");
        employee.setSchema(schema);
        employee.setDataStore(new InMemoryDataStore());
        employee.setIndexes(new HashMap<String, Tree>() {{
            put("ID", new SelfBalancingTree<Integer>());
        }});

        Column id = new Column();
        id.setName("ID");
        id.setSize(10);
        id.setNullable(false);
        id.setType(Integer.class);
        id.setTable(employee);

        Column name = new Column();
        name.setName("Name");
        name.setSize(50);
        name.setNullable(false);
        name.setType(String.class);
        name.setTable(employee);

        Column salary = new Column();
        salary.setName("Salary");
        salary.setSize(10);
        salary.setNullable(true);
        salary.setType(Double.class);
        salary.setTable(employee);

        Validator validator = new ValidatorImpl();


        //System.out.println(System.getProperty("user.dir"));
        //DataImporter importer = new FileDataImporterImpl(new File(ClassLoader.getSystemResource("employees.txt").getFile()), "tree",schema, validator);
        DataImporter importer = new FileDataImporterImpl(new File(args[1]), "tree", schema, validator);
        importer.importData();

        //Expression expression = new EqExpression(salary, Util.convertToType(salary.getType(),"5000"));
        List<Map<String, Comparable>> result = Criteria.parseSql(args[0], employee).eval();
        //List<Map<String, Comparable>> result = Criteria.parseSql("select   ID, Salary, Name Where ID >= 100 Order by ID", employee).eval();

        System.out.println(result);

    }
}
