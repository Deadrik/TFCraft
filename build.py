import os, os.path, sys
import shutil, fnmatch
import logging

forge_dir = os.path.dirname(os.path.abspath(__file__))
mcp_dir = os.path.abspath('..')
src_dir = os.path.join(mcp_dir, 'src')
backup_dir = os.path.join(mcp_dir, 'src_backup')

sys.path.append(mcp_dir)
from runtime.recompile import recompile

from base import copytree, reset_logger

def main():
    build_num = 0
    if len(sys.argv) > 1:
        try:
            build_num = int(sys.argv[1])
        except:
            pass
    sys.exit(build(build_num))
    
def build(major_num=0, build_num=0, revision_num=0):
    print '============================= Build Start (%d.%d.%d) ============================' % (major_num, build_num, revision_num)   
    print '\nsrc -> backup'
    copytree(src_dir, backup_dir, 0)    
    print '\nTFC_Shared -> minecraft'
    copytree(os.path.join(forge_dir, 'TFC_Shared'), os.path.join(src_dir, 'minecraft'), 0)
    print '\nTFC API -> minecraft'
    copytree(os.path.join(forge_dir, 'TFC API'), os.path.join(src_dir, 'minecraft'), 0)
    print
    
    error_level = 0
    try:
        os.chdir(mcp_dir)
        reset_logger()
        recompile(None, False, False)
        reset_logger()
        os.chdir(forge_dir)
    except SystemExit, e:
        print 'Recompile Exception: %d ' % e.code
        error_level = e.code

    print '\nremove src'
    shutil.rmtree(src_dir)
    print '\nbackup -> src'
    copytree(backup_dir, src_dir, 0)
    print '\nremove backup'
    shutil.rmtree(backup_dir)
        
    print '============================= Build Finished %d ============================' % error_level
    return error_level
    
if __name__ == '__main__':
    main()
