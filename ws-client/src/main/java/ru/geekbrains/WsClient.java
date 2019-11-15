package ru.geekbrains;

import ru.geekbrains.service.Category;
import ru.geekbrains.service.ToDoRepr;
import ru.geekbrains.service.ToDoService;
import ru.geekbrains.service.ToDoServiceWs;
import ru.geekbrains.service.CategoryService;
import ru.geekbrains.service.CategoryServiceWs;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;

public class WsClient {

    public static void main(String[] args) throws MalformedURLException, DatatypeConfigurationException {
        URL url = new URL("http://localhost:8130/todo-jsf/ToDoService/ToDoService?WSDL");

        ToDoService toDoService = new ToDoService(url);

        ToDoServiceWs toDoServicePort = toDoService.getToDoServicePort();

        ToDoRepr toDoRepr = new ToDoRepr();
        toDoRepr.setDescription("From SOAP service");
        toDoRepr.setPrice(123.45);
        toDoRepr.setCount(12);
        toDoRepr.setActive(true);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        toDoRepr.setTargetDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));

        Category category = new Category();
        category.setId(1);
        category.setDescription("Fruit");
        toDoRepr.setCategory(category);

        toDoServicePort.insert(toDoRepr);

        toDoServicePort.findAll()
                .forEach(t -> System.out.println(t.getDescription()));

        Long idToDo = 3L;
        toDoServicePort.findById(idToDo);

        toDoServicePort.delete(idToDo);

        String description = "Pears";
        toDoServicePort.findByDescription(description);

        Integer idCategory = 1;
        toDoServicePort.findByCategory(idCategory);

        CategoryService categoryService = new CategoryService(url);

        CategoryServiceWs categoryServicePort = categoryService.getCategoryServicePort();

        Category categoryNew = new Category();
        categoryNew.setDescription("Technic");
        categoryServicePort.insertCategory(categoryNew);

    }
}
