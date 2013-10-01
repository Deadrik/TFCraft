@echo off

set major_num=
set /p major_num=Please Enter the Major Number:
set build_num=
set /p build_num=Please Enter the Build Number:
set revision_num=
set /p revision_num=Please Enter the Revision Number:
..\runtime\bin\python\python_mcp release.py %major_num% %build_num% %revision_num%
pause