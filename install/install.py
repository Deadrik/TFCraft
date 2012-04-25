import os, os.path, sys
import urllib, zipfile
import shutil, glob, fnmatch
import subprocess, logging

forge_dir = os.path.dirname(os.path.abspath(__file__))
mcp_dir = os.path.abspath('..')
src_dir = os.path.join(mcp_dir, 'src')

sys.path.append(mcp_dir)
from runtime.decompile import decompile
from runtime.updatenames import updatenames
from runtime.updatemd5 import updatemd5
from runtime.cleanup import cleanup
from runtime.updatemcp import updatemcp

from tfc import apply_patches, copytree, reset_logger, download_ff

def main():
    print '=================================== TFCraft Setup Start ================================='
    if not os.path.isdir(src_dir):
        print 'Something went wrong, src folder not found at: %s' % src_dir
        sys.exit(1)
        
    has_client = os.path.isdir(os.path.join(mcp_dir, 'src', 'minecraft'))
    has_server = os.path.isdir(os.path.join(mcp_dir, 'src', 'minecraft_server'))
    
    reset_logger()
    os.chdir(forge_dir)
        
    print 'Applying TFCraft patches'
    if has_client:
        if os.path.isdir(os.path.join(forge_dir, 'patches', 'minecraft')):
            apply_patches(os.path.join(forge_dir, 'patches', 'minecraft'), src_dir)
    if has_server:
        if os.path.isdir(os.path.join(forge_dir, 'patches', 'minecraft_server')):
            apply_patches(os.path.join(forge_dir, 'patches', 'minecraft_server'), src_dir)
    
    print '=================================== Minecraft Forge Setup Finished ================================='
    
if __name__ == '__main__':
    main()