#Constants
WAR_FILE="generic_spring.war"
WAR_FILES="generic_spring"
CODE_BASE="/home/sachin/projects/generic_spring"
BUILD_LOC="/home/sachin/build_files"
CATALINA_HOME="/home/sachin/apache-tomcat-7_2"

#Copy application.development.properties to application.properties
#echo "Copying application.development.properties to application.properties"
#$(cp $CODE_BASE"/src/resources/application.development.properties" $CODE_BASE"/src/resources/application.properties")

#DO a git pull
echo "Doing a git pull from master"
$(cd $CODE_BASE)
$(git pull origin master)

#shut down tomcat
echo "Shutting down tomcat"
$(sudo $CATALINA_HOME/bin/shutdown.sh)

echo "Taking current war files back up"
#take current war file's backup
$(mv  $CATALINA_HOME/webapps/$WAR_FILE $CATALINA_HOME/webapps/backup/$(date +"%y-%m-%d")_$WAR_FILE)
$(sudo rm -r $CATALINA_HOME/webapps/$WAR_FILES)

#build war file
echo "Building war file"
#$(cd $CODE_BASE)
#$(jar -cf $WAR_FILE *)
#$(mv $WAR_FILE $BUILD_LOC/$WAR_FILE)
$(ant war)

#copy the new war file into webapps
#echo "Copying the new war file"
#$(mv  $BUILD_LOC/$WAR_FILE $CATALINA_HOME/webapps/$WAR_FILE)

#start the tomcat
echo "Starting tomcat"
$(sudo $CATALINA_HOME/bin/startup.sh)
echo "Deployment done!"


