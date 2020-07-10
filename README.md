# small-site-spring-log
<h3>v.0.0.7<h3>
<h4>Учебный проект</h4>

<p>Реализация простого сайта на Spring MVC и логгирования сайта (Tomcat, не Spring Boot) разными способами (через сигнатуру метода, логгирование исключений, через аннотации и т.д.) с помощью Spring AOP.</p>
<p>Используемый шаблонизатор - ThymeLeaf</p>
<p>На сайте реализованы шаблонные задачи: GET и POST запросы, отправка и получения Json Post запросом</p>
<p>Шаблонные задачи запланированные к реализации в проекте</p>
<ul>
<li>Работа с Cookie, запоминание пользователя - выполнено в 0.0.5.</li>
<li>Передача на сервер времени пользователя, с преобразованием его к UTC+00. Получение от сервера времени в UTC+00 
и передача его клиенту с преобразованием в местное для клиента (на стороне клиента). - выполнено в 0.0.6</li>
<li>Скачивание файла с сервера. - выполнено в 0.0.7</li>
<li>Передача файла на сервер.</li>
<li>Проигрывание музыки на сайте.</li>
</ul>

<h4>Добавлено обзорное видео по реализованным функциям, по версии 0.0.7 -  <a href="https://github.com/Novoselov-pavel/small-site-spring-log/blob/master/server_All.mp4">Видео</a></h4>

<p>Тесты контроллера на Mockito</p>
<p>Проект создается из архетипа Maven - webapp</p>
<h5>В процессе разработки...</h5>

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