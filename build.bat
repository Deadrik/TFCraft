@echo off
echo =================================== Build Start =================================
cd ..

rmdir /S /Q src
mkdir src

echo src_base -^> src
xcopy /Q /Y /E src_base\* src\
echo.

echo tfc_client -^> minecraft
xcopy /Y /E TFCraft\TFC_client\src\net\* src\minecraft\net
echo.

echo tfc_server -^> minecraft_server
xcopy /Y /E TFCraft\TFC_server\src\net\* src\minecraft_server\net
echo.

echo tfc_shared -^> minecraft
xcopy /Y /E TFCraft\tfc_shared\src\net\* src\minecraft\net
echo.

echo tfc_shared -^> minecraft_server
xcopy /Y /E TFCraft\tfc_shared\src\net\* src\minecraft_server\net
echo.


runtime\bin\python\python_mcp runtime\recompile.py

set ret=%ERRORLEVEL%
cd forge
echo =================================== Build Finished %ret% =================================
if %ret% NEQ 0 exit /b %ret%
pause