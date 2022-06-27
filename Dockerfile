FROM tomcat:8.5.38
#ARG TOMCAT_INSTALL_DEFAULT_WEBAPPS=no
#ENV TOMCAT_EXTRA_JAVA_OPTS=yes
#ENV TOMCAT_INSTALL_DEFAULT_WEBAPPS=no
EXPOSE 8080
RUN mv /usr/local/tomcat/webapps/ROOT /usr/local/tomcat/webapps/ROOT2
ADD target/ROOT.war /usr/local/tomcat/webapps/
#RUN mv /usr/local/tomcat/webapps/tela.war /usr/local/tomcat/webapps/ROOT.war
CMD chmod +x /usr/local/tomcat/bin/catalina.sh
CMD ["catalina.sh", "run"]