@echo off
setlocal
where gradle >nul 2>nul
if errorlevel 1 (
  echo Gradle wurde nicht gefunden.
  echo Installiere Gradle oder oeffne das Projekt in IntelliJ IDEA mit Gradle-Unterstuetzung.
  pause
  exit /b 1
)
gradle build
if errorlevel 1 (
  echo Build fehlgeschlagen. Bitte die Fehlermeldung oben pruefen.
  pause
  exit /b 1
)
echo Fertig. Die JAR sollte in build\libs liegen.
pause
