#!/bin/bash

REPO_PATH="${PROJECT_HOME}/sustainapp/"

cd "${REPO_PATH}" && git pull origin master || :
git push github master 
exit 0
