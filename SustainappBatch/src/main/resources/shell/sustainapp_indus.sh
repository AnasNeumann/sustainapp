#!/bin/bash
#Author: Idriss NEUMANN <neumann.idriss@gmail.com>

GIT_REPO_URL="http://91.121.80.56:8181/gitlab/uprodit/uprodit.git"
SAVE_DIR="/BACK_PRODIT/old"
GIT_DIR="/BACK_PRODIT/export_git"
BATCH_DIR="$GIT_DIR/prodit-batch"

usage(){
	echo "Usage: ./livraison.sh [options]"
	echo "-h : afficher l'aide"
	echo "-u : mettre à jour le script de livraison"
	echo "-b : fabriquer les war à partir du trunk"
	echo "-d : deploy du dernier war du module prodit-ui sur le serveur tomcat public"
	echo "-f : deploy du module prodit-forum"
	echo "-w : deploy du dernier war du module prodit-ws sur le serveur tomcat privé"
	echo "-s : deploy du dernier jar du module prodit-se"
	echo "-p : purger les logs"
	echo "-k : purger les logs avec arret des tomcats"
	echo "-i : ré-indexer les index ElasticSearch"
	echo "-v : visualiser les indexes ElasticSearch"
	echo "--repair : réparer les checksums de la bdd"
	echo "--repairtest : réparer les checksums de la bdd de tests"
	echo "--initbddtest : ré-initialiser la bdd de tests"
	echo "--bdd : mettre à jour la bdd"
	echo "--bddtest : mettre à jour la bdd de tests"
	echo "--backup : backup de la base de données PostgreSQL (avec rotation)"
	echo "--android : fabriquer l'application Android"
	echo "--android-debug : fabriquer l'application Android pour debugger"
	echo "--public : rédemarrer le serveur tomcat public"
	echo "--private : rédemarrer le serveur tomcat private"
	echo "--testforum : deploy du module prodit-forum de test"
}

error(){
	echo "Erreur : parametres invalides !" >&2
	echo "Utilisez l'option -h pour en savoir plus" >&2
	exit 1
}

git_update(){
	[[ $1 ]] && branche=$1 || branche="master"
	
	if [[ ! -d "$GIT_DIR" ]]; then
		git clone "$GIT_REPO_URL" "$GIT_DIR"
		cd "$GIT_DIR"
		git config credential.helper store
	fi
	
	cd "$GIT_DIR"
	
	git stash
	git stash clear
	git checkout "$branche"
	git stash
	git stash clear
	git pull
}

install_bdd(){
	git_update "$3"
	cd "$BATCH_DIR"
	mvn install pre-integration-test -Dmaven.test.skip=false -Dpurge.skip=$1 -Dipbd=localhost -Dusername=prodit_webi -Dpassword=prodit_webi -Ddbname=prodit"$2" flyway:migrate
	rm -rf /conf-webapps/prodit_cache*
}

repair_bdd(){
	git_update "$2"
	cd "$BATCH_DIR"
	mvn install pre-integration-test -Dmaven.test.skip=false -Dpurge.skip=true -Dipbd=localhost -Dusername=prodit_webi -Dpassword=prodit_webi -Ddbname=prodit"$1" flyway:repair
}

purge_index(){
	echo "Purge des indexes"
	curl -XGET 'http://localhost:9980/prodit-se/api/reindexall' >/dev/null
	
	echo "Indexations des utilisateurs du chat"
	curl -XGET 'http://localhost:9980/prodit-se/api/chatUserService/index' >/dev/null
	
	echo "Indexations des utilisateurs du profils personnels"
	curl -XGET 'http://localhost:9980/prodit-se/api/profilPersonnelService/index' >/dev/null
	
	echo "Indexations des utilisateurs du profils entreprise"
	curl -XGET 'http://localhost:9980/prodit-se/api/entrepriseService/index' >/dev/null
	
	echo "Indexations des utilisateurs du profils associations"
	curl -XGET 'http://localhost:9980/prodit-se/api/associationService/index' >/dev/null
	
	echo ""
}

consult_index(){
	echo "Contenu des indexes"
	curl 'localhost:9902/_search?pretty'
}

purge_log(){
	for file in /usr/local/apache-tomcat-8.0.22-*/logs/*; do 
		[[ $(echo $file|grep -Eo "[0-9]{4}-[0-9]{2}-[0-9]{2}") < $(date +"%Y-%m-%d") ]] && rm -rf $file
	done
	
	rm -rf /usr/local/apache-tomcat-8.0.22-volifecycle/logs/*
	
	# Pour en finir avec le $? = 1 lorsque pas de résultats
	echo "done"
}

purge_log_stop(){
	rm -rf /BACK_PRODIT/prodit-se.log
	
	declare -a tomcats
	tomcats[0]=/home/prodit/apache-tomcat-8.0.22-volifecycle/
	tomcats[1]=/home/prodit/apache-tomcat-8.0.22-public/
	tomcats[2]=/home/prodit/apache-tomcat-8.0.22-private/
	
	oldDir="$(pwd)"
	
	# Arret des tomcats
	for i in "${tomcats[@]}"; do
		echo "Purge $i"
		cd $i
		./bin/shutdown.sh
		rm -rf ./logs/*
		./bin/startup.sh
		sleep 10
	done
	
	cd "$oldDir"
}	

# maj du script courrant
update(){
	path_install="/BACK_PRODIT/install.sh"
	echo "-------------------------------------------------------"
	echo "----------Mise à jour du script install.sh"
	git_update "$1"
	cp -f "$BATCH_DIR/src/main/resources/shell/prodit_indus.sh" "$path_install"
	dos2unix "$path_install"
	chmod +x "$path_install"
}

# maj du script courrant
pgbackup(){
	echo "-------------------------------------------------------"
	echo "----------Mise à jour des scripts SH"
	git_update "$1"
	
	for i in "$BATCH_DIR/src/main/resources/shell/pgdump"/*; do
	    dos2unix "$i"
	    chmod +x "$i"
	done
	
	bash "$BATCH_DIR/src/main/resources/shell/pgdump/pg_backup_rotated.sh"
}

deploy_ui(){
	dir_webapp_tomcat_server="/usr/local/apache-tomcat-8.0.22-public/webapps"
	path_war="./"
	
	rm -rf "$SAVE_DIR"
	war="$(find "$path_war" -iname "prodit-ui*.war"|head -1)"
	war2="./ROOT.war"
	
	if [[ ! -f $war ]]; then
		echo "War inexistant !" >&2
		exit 1
	fi
	
	[[ "$war" != "$war2" ]] && cp "$war" "$war2"
	[[ ! -d "SAVE_DIR" ]] && mkdir "$SAVE_DIR"
	cp "$dir_webapp_tomcat_server"/"$war2" "$SAVE_DIR"
	cp "$war2" "$dir_webapp_tomcat_server"
}

deploy_forum(){
	echo "----------DEBUT----------------------------------------"
	path="/BACK_PRODIT/prodit-forum$1"
	pathSave="/BACK_PRODIT/prodit-forum_save"
	pathConf="/BACK_PRODIT/conf"
	
	if [[ $1 != "_test" ]]; then
		rm -rf "$pathSave"
		mkdir "$pathSave"
		cp -Rf "$path/files" "$pathSave"
		cp -Rf "$path/cache" "$pathSave"
		cp -Rf "$path/images/avatars/upload" "$pathSave"
		cp -Rf "$path/.htaccess" "$pathSave"
	fi
	
	echo "----------Nettoyage de la version precedente"
	rm -rf "$path"
	rm -rf "$pathConf"
	rm -rf "/var/www/html/forum$1"
	[[ -d $pathConf ]] || mkdir "$pathConf"
	
	echo "-------------------------------------------------------"
	echo "----------Checkout trunk"
	git_update "$2"
	cp -R "$GIT_DIR/prodit-forum" "$path"
	cp -R "$GIT_DIR/prodit-batch/src/main/resources/env" "$pathConf"
	
	if [[ $1 = "_test" ]]; then
		cp "$pathConf/test/conf/prodit-forum/config.php" "$path"
	else
		cp -Rf "$pathSave/files" "$path"
		cp -Rf "$pathSave/cache" "$path"
		cp -Rf "$pathSave/upload" "$path/images/avatars/"
		cp -Rf "$pathSave/.htaccess" "$path"
		
		cp "$pathConf/env/prd/conf/prodit-forum/config.php" "$path"
	fi 
	
	ln -s "$path" "/var/www/html/forum$1"
	chmod -R 777 "$path"
}

deploy_ws(){
	dir_webapp_tomcat_server="/usr/local/apache-tomcat-8.0.22-private/webapps"
	path_war="./"
	
	rm -rf "$SAVE_DIR"
	war="$(find "$path_war" -iname "prodit-ws*.war"|head -1)"
	war2="./prodit-ws.war"
	
	if [[ ! -f $war ]]; then
		echo "War inexistant !" >&2
		exit 1
	fi
	
	[[ "$war" != "$war2" ]] && cp "$war" "$war2"
	[[ ! -d "SAVE_DIR" ]] && mkdir "$SAVE_DIR"
	cp "$dir_webapp_tomcat_server"/"$war2" "$SAVE_DIR"
	cp "$war2" "$dir_webapp_tomcat_server"
}

deploy_se(){
	path_war="./"
	
	war="$(find "$path_war" -iname "prodit-se*-fat.jar"|head -1)"
	war2="./prodit-se-fat.jar"
	
	if [[ ! -f $war ]]; then
		echo "War inexistant !" >&2
		exit 1
	fi
	
	[[ "$war" != "$war2" ]] && cp "$war" "$war2"
	
	echo "Arret du composant prodit-se"
	ps -ef | grep prodit-se | grep java | awk '{print $2}' | while read -r; do kill -9 "$REPLY"; done
	
	echo "Démarrage du composant prodit-se"
	chmod +x "$war2"
	nohup java -jar "$war2" -conf "./config.json" >> prodit-se.log 2>&1 &
	sleep 2
	curl "http://localhost:9980/prodit-se/api/v1/jobOffers/tweets/index"
	disown
}

android(){
	echo "----------DEBUT----------------------------------------"
	echo "-------------------------------------------------------"
	echo "----------Checkout android"
	git_update "$2"
	
	currentPath="$(pwd)"
	cd "$GIT_DIR/prodit-mobile/prodit-android-web"
	rm -rf bin/*
	
	echo "-------------------------------------------------------"
	echo "----------Release uprodit${1}.apk"
	ant release
	cp bin/uprodit-release.apk "/var/www/html/android/uprodit${1}.apk"
	cd "$currentPath"
}

war_trunk(){
	VERSION="trunk"
	
	echo "----------DEBUT----------------------------------------"
	echo "----------Nettoyage de la version precedente"
	rm -rf *.war
	rm -rf *fat.jar

	echo "-------------------------------------------------------"
	echo "----------Checkout trunk"
	git_update "$1"
	
	currentPath="$(pwd)"
	cd "$GIT_DIR"
	
	echo "-------------------------------------------------------"
	echo "----------Changement des fichiers de conf"
	pathEnv=./prodit-batch/src/main/resources/env/prd/conf
	cp $pathEnv/prodit-ui/* prodit-ui/src/main/resources
	cp $pathEnv/prodit-ws/* prodit-ws/src/main/resources
	cp $pathEnv/prodit-se/* prodit-se/src/main/resources
	
	echo "-------------------------------------------------------"
	echo "----------Build de PRODIT"
	mvn clean install -Dmaven.test.skip=true -Djacoco.skip=true -Dfindbugs.skip=true -Dcheckstyle.skip=true -Dwadl.skip=true
	
	WAR="prodit-ui-$VERSION.war"
	pathWAR="$GIT_DIR/prodit-ui/target/prodit-ui-0.0.1-SNAPSHOT.war"
	cd "$currentPath"
	if [[ ! -f $pathWAR ]]; then
	    echo "Erreur : le fichier $WAR n'existe pas..." >&2
	    retour="1"
	else
		cp "$pathWAR" "./$WAR"
		chmod -R 777 "./$WAR"
		retour="0"
	fi
	
	WAR="prodit-ws-$VERSION.war"
	pathWAR="$GIT_DIR/prodit-ws/target/prodit-ws-0.0.1-SNAPSHOT.war"
	if [[ ! -f $pathWAR ]]; then
	    echo "Erreur : le fichier $WAR n'existe pas..." >&2
	    retour="1"
	else
		cp "$pathWAR" "./$WAR"
		chmod -R 777 "./$WAR"
		retour="0"
	fi
	
	WAR="prodit-se-$VERSION-fat.jar"
	pathWAR="$GIT_DIR/prodit-se/target/prodit-se-0.0.1-SNAPSHOT-fat.jar"
	pathCONF="$GIT_DIR/prodit-se/src/main/resources/config.json"
	if [[ ! -f $pathWAR ]]; then
	    echo "Erreur : le fichier $WAR n'existe pas..." >&2
	    retour="1"
	else
		cp "$pathWAR" "./$WAR"
		cp "$pathCONF" "./"
		chmod -R 777 "./$WAR"
		retour="0"
	fi
	
	echo "----------FIN------------------------------------------"
	exit "$retour"
}

server_restart(){
	bash "/usr/local/apache-tomcat-8.0.22-$1/bin/shutdown.sh"
	sleep 2
	bash "/usr/local/apache-tomcat-8.0.22-$1/bin/startup.sh"
}

[[ $# -lt 1 ]] && error

options=$(getopt -o u,h,b,d,w,s,p,k,i,v,f -l android,android-debug,help,update,build,deploy,forum,ws,se,purgelog,purgelogstop,index,showindex,initbddtest,bdd,bddtest,updatebdd,updatebddtest,backup,public,private,testforum,repair,repairtest -- "$@") 
set -- $options 
while true; do 
    case "$1" in 
        -u|--update) update ; shift ;;
        -h|--help) usage ; shift ;;
        -b|--build) war_trunk ; shift ;;
        -d|--deploy) deploy_ui ; shift ;;
        -f|--forum) deploy_forum ; shift ;;
        --testforum) deploy_forum "_test" ; shift ;;
        -w|--ws) deploy_ws ; shift ;;
        -s|--se) deploy_se ; shift ;;
        -p|--purgelog) purge_log ; shift ;;
        -k|--purgelogstop) purge_log_stop ; shift ;;
        -i|--index) purge_index ; shift ;;
        -v|--showindex) consult_index ; shift ;;
        --initbddtest) install_bdd false _test ; shift ;;
        --bdd|--updatebdd) install_bdd true _lot2 ; shift ;;
        --repair) repair_bdd _lot2 ; shift ;;
        --repairtest) repair_bdd _lot2 ; shift ;;
        --bddtest|--updatebddtest) install_bdd true _test ; shift ;;
        --backup) pgbackup ; shift ;;
        --android) android ""; shift ;;
        --android-debug) android "-debug"; shift ;;
        --public) server_restart "public"; shift ;;
        --private) server_restart "private"; shift ;;
        --) shift ; break ;; 
        *) error ; shift ;;
    esac 
done
