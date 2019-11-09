package ru.geekbrains;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

// подключение библиотеки Jersey
// api - общая часть пути web-приложения к web-сервису
@ApplicationPath("api")
public class RestApplication extends Application {

}
