#!/bin/bash

if [[ $# -lt 3 ]]; then
    echo "Usage: ./$0 <old-email> <new-email> <new-name>"
    exit 1
fi

git filter-branch -f --env-filter '
OLD_EMAIL="'"$1"'"
CORRECT_NAME="'"$3"'"
CORRECT_EMAIL="'"$2"'"
if [ "$GIT_COMMITTER_EMAIL" = "$OLD_EMAIL" ]; then
    export GIT_COMMITTER_NAME="$CORRECT_NAME"
    export GIT_COMMITTER_EMAIL="$CORRECT_EMAIL"
fi
if [ "$GIT_AUTHOR_EMAIL" = "$OLD_EMAIL" ]; then
    export GIT_AUTHOR_NAME="$CORRECT_NAME"
    export GIT_AUTHOR_EMAIL="$CORRECT_EMAIL"
fi
' --tag-name-filter cat -- --branches --tags
