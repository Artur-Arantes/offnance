LAST_COMMIT=$(git log -1 --pretty=%B | head -n1)
COMMIT_REGEX="^(build|chore|ci|docs|fix|perf|refactor|revert|style|test|feat)(\(.+\))?:(.+)$"
if [[ $LAST_COMMIT =~ $COMMIT_REGEX ]]; then
  VER="${BASH_REMATCH[1]}"
  TASK="${BASH_REMATCH[2]}"
  DESC="${BASH_REMATCH[3]}"
  OLD_VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
  IS_MINOR="(build|chore|ci|docs|fix|perf|refactor|revert|style|test)"
  VERSION_MATCH="^([0-9]+)\.([0-9]+)\.([0-9]+)"
  if [[ $LAST_COMMIT =~ IS_MINOR ]]; then
    if [[ $OLD_VERSION =~ $VERSION_MATCH ]]; then
      BREAK="${BASH_REMATCH[1]}"
      MAJOR="${BASH_REMATCH[2]}"
      MINOR="${BASH_REMATCH[3]}"
      MINOR="$(echo "$MINOR+1" | bc)"
    fi
  else
    if [[ $OLD_VERSION =~ $VERSION_MATCH ]]; then
      BREAK="${BASH_REMATCH[1]}"
      MAJOR="${BASH_REMATCH[2]}"
      MINOR="${BASH_REMATCH[3]}"
      MAJOR="$(echo "$MINOR+1" | bc)"
    fi
  fi
  printf "$(echo "\n## $BREAK.$MAJOR.$MINOR TASK $TASK\n -$DESC")" >> changelog.md
  NEW_VERSION="$(echo "$BREAK.$MAJOR.$MINOR")"
  echo "Releasing $OLD_VERSION as $NEW_VERSION"
  git config user.name "Release Script"
  git config user.email "builds@offnance-circle-ci.com"
  mvn versions:get -DnewVersion=NEW_VERSION
  git add pom.xml
  git add changelog.md
  git commit -m "chore: $NEW_VERSION"
  git tag -f "$NEW_VERSION"
  git checkout main
  git merge --no-edit release
  git push -f origin main --tags
  NEW_VERSION="$(echo "$BREAK.$MAJOR.$MINOR")-SNAPSHOT"
  mvn versions:get -DnewVersion=NEW_VERSION
  git add pom.xml
  git commit -m "fix: add snapshot version"
  git push -f origin main
  exit 0
fi
echo "Não foi possível fazer release"
exit 1