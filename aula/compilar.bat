@echo off
cd /d "%~dp0"

echo Compilando projeto...
javac -encoding UTF-8 -d target\classes -cp "src\main\java" src\main\java\com\example\aula\*\*.java src\main\java\com\example\aula\connection\*.java src\main\java\com\example\aula\dao\*.java src\main\java\com\example\aula\model\*.java

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Compilacao concluida! Rodando aplicacao...
    echo.
    java -cp target\classes com.example.aula.ExemploListaBIM1
) else (
    echo Erro na compilacao!
    pause
)
