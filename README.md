# small-site-spring-log
<h3>v.0.0.1<h3>
<h4>Учебный проект</h4>

<p>Реализация логгирования простого сайта (Tomcat, не Spring Boot) разными способами (через сигнатуру метода, логгирование исключений, через аннотации и т.д.) с помощью Spring AOP.</p>
<p>Использемый шаблонизатор - ThymeLeaf</p>
<p>Проект создается из архетипа Maven - webapp</p>
<h5>В процессе разработки...</h5>

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