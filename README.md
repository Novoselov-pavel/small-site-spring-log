# small-site-spring-log
<h3>v.2.0.0<h3>
<h4>Учебный проект</h4>

<p>Реализация простого сайта на Spring MVC и логгирования сайта (Tomcat, не Spring Boot) разными способами (через сигнатуру метода, логгирование исключений) с помощью Spring AOP.</p>
<p>Используемый шаблонизатор - ThymeLeaf</p>
<p>На сайте реализованы шаблонные задачи:</p>
<details><summary><b>Запросы</b></summary>

<p><b>Enum в классе RequestPageStorage</b> из пакета package com.npn.spring.learning.logger.smallsite.models описывает
 запросы и связывает их с представлениями</p>
<p>Контроллер для этих запросов <b>HelloController</b> из пакета package com.npn.spring.learning.logger.smallsite.controllers</p>
<p>Реализованные следующие запросы:</p>
<ul>
<li>GET через window.location.href</li>
<li>GET запросом через html form</li>
<li>POST через html form</li>
<li>POST через Thymeleaf form</li>
<li>POST через JavaScript и Json, оправка и получение данных</li>
</ul>

</details>
<details><summary><b>Стандартные задачи</b></summary>

<p><b>Enum в классе OperationPageStorage</b> из пакета com.npn.spring.learning.logger.smallsite.models описывает
 запросы и связывает их с представлениями</p>
<p>Контроллер для этих запросов <b>OperationController</b> из пакета com.npn.spring.learning.logger.smallsite.controllers</p>
<p>Реализованные следующие задачи:</p>
<ul>
<li>Запоминание пользователя через cookie на 30 секунд.</li>
<li>Отправка даты и времени на сервер, перекодирование из локального в UTC и получение даты от сервера.</li>
<li>Скачивание файла с сервера (динамическое получение файла) через GET запрос</li>
<li>Загрузка файла на сервер</li>
<li>Проигрывание музыки</li>
</ul>


</details>

<details><summary><b>Общая, справочная информация</b></summary>

<ul>
<li>Контроллер MainPageController из пакета com.npn.spring.learning.logger.smallsite.controllers является контроллером основной страницы,
 и страницы-заместителя при ошибках.</li>
<li>Контроллер ScriptController из пакета com.npn.spring.learning.logger.smallsite.controllers является контроллером работы 
 с запросами выдающими/получающими данные не в виде формы,а в виде Json, тела запроса и т.п. 
</li>
<li>Представления находятся по адресу src/main/webapp/WEB-INF/views</li>
<li>Настройки xml находятся по адресу src/main/webapp/WEB-INF</li>
<li>Внешние скрипты JS находятся по адресу src/main/resources/viewsresource/scripts/my</li>
<li>Классы, выполняющие логгирование находятся в пакете com.npn.spring.learning.logger.smallsite.loggers</li>
<li>Для версии с настройками из Java кода, конфигурационные классы находятся в пакете com.npn.spring.learning.logger.smallsite.configs</li>
</ul>


</details>

<h4>Добавлено обзорное видео по реализованным функциям, по версии 1.0.0 -  <a href="https://github.com/Novoselov-pavel/small-site-spring-log/blob/master/server_All.mp4">Видео</a></h4>

<p>Тесты контроллера на Mockito</p>
<p>Проект создается из архетипа Maven - webapp</p>
<h5>Внимание:</h5>
<p>Переменные, которые связаны с конкретным компьютером (расположение путей для папок с динамическими ресурсами)
указаны в <a href="https://github.com/Novoselov-pavel/small-site-spring-log/blob/master/src/main/resources/settings.properties">settings.properties</a>.</p>
<p>Еще, подобные переменные есть в тестах: PickFromFilesDriverTest, ScriptControllerTest.</p>

<h5>Версия 1.0.0 - инициализация XML файлами закончена.</h5>
<h5>Версия 2.0.0 - инициализация с помощью java кода закончена. Версии находятся в соответствующих branch-ах.</h5>

<details><summary>История версий</summary>

<h5>Версия 2.0.0</h5>
<p>Изменено конфигурирование, на конфигурирование из Java кода.
</p>

<h5>Версия 1.0.0</h5>
<p>Внесены некоторые изменения. В связи с ConcurrentModificationException List-ы заменены на потоковобезопасные - CopyOnWriteArrayList. 
Добавлен метод логгирования исключений и перенаправления на страницу сообщающую об ошибке на основе Spring AOP.
</p>


<h5>Версия 0.0.9</h5>
<p>Реализован проигрыватель музыки на основе HTML Audio посредством JavaScript.
</p>


<h5>Версия 0.0.8</h5>
<p>Выполнена загрузка файлов на сервер через Ajax. Выполнены тесты.
</p>

<h5>Версия 0.0.7.a</h5>
<p>Выполнена привязка папки для компонента карусель из файла settings.properties (operation.form.sendFile.dir).
Добавлены тесты.
</p>

<h5>Версия 0.0.7</h5>
<p>Выполнена реализация скачивания данных с сервера (не статические объекты, через GET-запрос).
</p>

<h5>Версия 0.0.6</h5>
<p>Выполнена передача данных на сервер/с сервера в формате text/plain.
Исправлены ошибки в работе с Cookie
</p>


<h5>Версия 0.0.5</h5>
<p>Изменена адресация страниц с применением аннотации @RequestMapping() для контролллеров.
Произведен рефакторинг и добавление контроллеров.
Добавлено меню разработки шаблонных задач.
Добавлена работа с Cookie - запоминание пользователя с редиректом на текущую страницу.
Добавлены тесты для работы с Cookie.
</p>

<h5>Версия 0.0.4.a</h5>
<p>Рефакторинг. Некоторое изменение верстки страниц для более красивого дизайна. 
Закончены все задачи связанные с обработкой простых запросов</p>

<h5>Версия 0.0.4</h5>
<p>Некоторые изменения в классах для тестов Mockito и добавление тестов для HelloController GET и POST запросы</p>
<p>Внесены некоторые изменения по результатам тестов в контроллер</p>

<h5>Версия 0.0.3</h5>
<p>Добавлено логгирование методов пакета model через AOP и XML-конфигурацию</p>

<h5>Версия 0.0.2</h5>
<p>Реализована работа с POST и GET запросами через Json</p>

<h5>Версия 0.0.1</h5>
<p>Реализована работа с POST и GET запросами, использование фрагментов ThymeLeaf для более кастомной шаблонизации</p>

</details>



<h5>Для настройки Tomcat на кодировку UTF-8 требуется выполнить дополнительные действия:</h5>
<ul>
<li>В папке расположения Tomcat зайти в подпапку conf.</li>
<li>В файле server.hml для всех используемых коннекторов добавить URIEncoding="UTF-8" например:<pre>
&lt;Connector port=&quot;8080&quot; protocol=&quot;HTTP/1.1&quot;
               URIEncoding=&quot;UTF-8&quot;
               connectionTimeout=&quot;20000&quot;
               redirectPort=&quot;8443&quot;/&gt;
</pre>
</li>
<li>В файле web.hml разкомментировать строки:<pre>    
&lt;filter-mapping&gt;
   &lt;filter-name&gt;setCharacterEncodingFilter&lt;/filter-name&gt;
        &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
&lt;/filter-mapping&gt;
&lt;filter&gt;
        &lt;filter-name&gt;setCharacterEncodingFilter&lt;/filter-name&gt;
        &lt;filter-class&gt;org.apache.catalina.filters.SetCharacterEncodingFilter&lt;/filter-class&gt;
        &lt;init-param&gt;
            &lt;param-name&gt;encoding&lt;/param-name&gt;
            &lt;param-value&gt;UTF-8&lt;/param-value&gt;
        &lt;/init-param&gt;
        &lt;async-supported&gt;true&lt;/async-supported&gt;
&lt;/filter&gt;
</pre>
</li> 
</ul>