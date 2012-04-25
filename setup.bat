@echo off
..\runtime\bin\python\python_mcp setup.py %* -skipdecompile
if NOT "%1"=="-skipdecompile" (
  pause
)