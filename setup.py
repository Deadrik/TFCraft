import os, os.path, sys
import urllib, zipfile
import shutil, glob, fnmatch
import subprocess, logging

forge_dir = os.path.dirname(os.path.abspath(__file__))
mcp_dir = os.path.abspath('..')
src_dir = os.path.join(mcp_dir, 'src')

sys.path.append(mcp_dir)
from base import apply_patches, copytree


def main():
    print '=================================== Setup Start ================================='
    
    if not os.path.isdir(src_dir):
        print 'Something went wrong, src folder not found at: %s' % src_dir
        sys.exit(1)
    
    os.chdir(forge_dir)
    
    base_dir = os.path.join(mcp_dir, 'src_base')
    work_dir = os.path.join(mcp_dir, 'src')
    
    if os.path.isdir(base_dir):
        shutil.rmtree(base_dir)
        
    print 'Setting up source directories'    
    shutil.copytree(src_dir, base_dir)
    
    print 'Applying TFC patches'
    apply_patches(os.path.join(forge_dir, 'patches'), work_dir)
    
    print '=================================== Setup Finished ================================='   

if __name__ == '__main__':
    main()
