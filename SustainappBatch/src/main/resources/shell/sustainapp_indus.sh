#!/bin/bash
#Author: Idriss NEUMANN <neumann.idriss@gmail.com>

GIT_REPO_URL="http://91.121.80.56:8181/gitlab/isamm/sustainapp.git"
INSTALL_DIR="/BACK_SUSTAINAPP"
SAVE_DIR="${INSTALL_DIR}/old"
GIT_DIR="${INSTALL_DIR}/export_git"
BATCH_DIR="${GIT_DIR}/SustainappBatch"
TOMCAT_VERSION="8.0.22"
SUSTAINAPP_BACK_LOGFILE="${INSTALL_DIR}/sustainapp-back.log"

usage(){
	echo "Usage: ./livraison.sh [options]"
	echo "-h : afficher l'aide"
	echo "-u : mettre à jour le script de livraison"
	echo "-b : fabriquer les artifacts à partir du master"
	echo "-d : deploy de l'artifact back"
	echo "-p : purger les logs"
	echo "--repair : réparer les checksums de la bdd"
	echo "--bdd : mettre à jour la bdd"
	echo "--backup : backup de la base de données PostgreSQL (avec rotation)"
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
	mvn install pre-integration-test -Dmaven.test.skip=false -Dpurge.skip=$1 -Dipbd=localhost -Dusername=sustainapp_admin -Dpassword=sustainapp -Ddbname=sustainapp"$2" flyway:migrate
	rm -rf /conf-webapps/sustainapp_cache*
}

repair_bdd(){
	git_update "$2"
	cd "$BATCH_DIR"
	mvn install pre-integration-test -Dmaven.test.skip=false -Dpurge.skip=true -Dipbd=localhost -Dusername=sustainapp_admin -Dpassword=sustainapp -Ddbname=sustainapp"$1" flyway:repair
}

purge_log(){
	for file in /usr/local/apache-tomcat-${TOMCAT_VERSION}-*/logs/*; do 
		[[ $(echo $file|grep -Eo "[0-9]{4}-[0-9]{2}-[0-9]{2}") < $(date +"%Y-%m-%d") ]] && rm -rf $file
	done
	
	rm -rf /usr/local/apache-tomcat-${TOMCAT_VERSION}-jenkins/logs/*
        echo "" > ${SUSTAINAPP_BACK_LOGFILE}

	# Pour en finir avec le $? = 1 lorsque pas de résultats
	echo "done"
}

# maj du script courrant
update(){
	path_install="${INSTALL_DIR}/install.sh"
	echo "-------------------------------------------------------"
	echo "----------Mise à jour du script install.sh"
	git_update "$1"
	cp -f "${BATCH_DIR}/src/main/resources/shell/sustainapp_indus.sh" "$path_install"
	dos2unix "$path_install"
	chmod +x "$path_install"
}

# maj du script courrant
pgbackup(){
	echo "-------------------------------------------------------"
	echo "----------Mise à jour des scripts SH"
	git_update "$1"
	
	for i in "${BATCH_DIR}/src/main/resources/shell/pgdump"/*; do
	    dos2unix "$i"
	    chmod +x "$i"
	done
	
	bash "${BATCH_DIR}/src/main/resources/shell/pgdump/pg_backup_rotated.sh"
}

deploy_back(){
	path_war="./"
	
	war="$(find "$path_war" -iname "SustainappServer-*.jar"|head -1)"
	war2="./SustainappServer.jar"
	
	if [[ ! -f $war ]]; then
		echo "War inexistant !" >&2
		exit 1
	fi
	
	[[ "$war" != "$war2" ]] && cp "$war" "$war2"
	
	echo "Arret du composant SustainappServer"
	ps -ef | grep SustainappServer | grep java | awk '{print $2}' | while read -r; do kill -9 "$REPLY"; done
	
	echo "Démarrage du composant SustainappServer"
	chmod +x "$war2"
	nohup java -jar "$war2" >> ${SUSTAINAPP_BACK_LOGFILE} 2>&1 &
	disown
}

war_trunk(){
	VERSION="trunk"
	
	echo "----------DEBUT----------------------------------------"
	echo "----------Nettoyage de la version precedente"
	rm -rf *.war
	rm -rf *.jar

	echo "-------------------------------------------------------"
	echo "----------Pull master"
	git_update "$1"
	
	currentPath="$(pwd)"
	cd "$GIT_DIR"
	
	echo "-------------------------------------------------------"
	echo "----------Changement des fichiers de conf"
	pathEnv=./SustainappBatch/src/main/resources/env/prd/conf
	cp ${pathEnv}/SustainappServer/* SustainappServer/src/main/resources
	
	echo "-------------------------------------------------------"
	echo "----------Build de SustainappServer"
	mvn clean package -Dmaven.test.skip=true
	
	WAR="SustainappServer.jar"
	pathWAR="${GIT_DIR}/SustainappServer/target/SustainappServer-0.0.1-SNAPSHOT.jar"
	if [[ ! -f $pathWAR ]]; then
	    echo "Erreur : le fichier $WAR n'existe pas..." >&2
	    retour="1"
	else
            cp "$pathWAR" "./$WAR"
            chmod -R 777 "./$WAR"
	    retour="0"
	fi
	
	echo "----------FIN------------------------------------------"
	exit "$retour"
}

[[ $# -lt 1 ]] && error

options=$(getopt -o u,h,b,d,w,s,p,k,i,v,f -l android,android-debug,help,update,build,deploy,forum,ws,se,purgelog,purgelogstop,index,showindex,initbddtest,bdd,bddtest,updatebdd,updatebddtest,backup,public,private,testforum,repair,repairtest -- "$@") 
set -- $options 
while true; do 
    case "$1" in 
        -u|--update) update ; shift ;;
        -h|--help) usage ; shift ;;
        -b|--build) war_trunk ; shift ;;
        -d|--deploy) deploy_back ; shift ;;
        -p|--purgelog) purge_log ; shift ;;
        --bdd|--updatebdd) install_bdd true "" ; shift ;;
        --repair) repair_bdd "" ; shift ;;
        --backup) pgbackup ; shift ;;
        --) shift ; break ;; 
        *) error ; shift ;;
    esac 
done
