import os, os.path, sys
import shutil, fnmatch
import logging, zipfile

forge_dir = os.path.dirname(os.path.abspath(__file__))
mcp_dir = os.path.abspath('..')
src_dir = os.path.join(mcp_dir, 'src')

sys.path.append(mcp_dir)
from runtime.reobfuscate import reobfuscate

from tfc import reset_logger, load_version, zip_folder, zip_create, inject_version
from build import build

reobf_dir = os.path.join(mcp_dir, 'reobf')
client_dir = os.path.join(reobf_dir, 'minecraft')
server_dir = os.path.join(reobf_dir, 'minecraft_server')
zip = None
zip_name = None
zip_base = None
version_str = None

def main():
    global version_str
    build_num = 0
    if len(sys.argv) > 1:
        try:
            build_num = int(sys.argv[1])
        except:
            pass
    ret = 0
    ret = build(build_num)
    if ret != 0:
        sys.exit(ret)
    
    print '=================================== Release Start ================================='
    error_level = 0
    try:
        os.chdir(mcp_dir)
        reset_logger()
        print 'reobf start'
        reobfuscate(None, False, True, True, True, False, False)
        print 'reobf end'
        reset_logger()
        os.chdir(forge_dir)
    except SystemExit, e:
        print 'Reobfusicate Exception: %d ' % e.code
        error_level = e.code
    
    version = load_version(build_num)
        
    out_folder = os.path.join(mcp_dir, 'TFC Build')
    if os.path.isdir(out_folder):
        shutil.rmtree(out_folder)
        
    os.makedirs(out_folder)
    
    zip_start('TFCraft-Universal-Core-B2-Build.zip')
    zip_folder(client_dir, '', zip)
    zip_add('TFCraft_credits.txt')
    zip_add('license.txt')
    zip_folder(os.path.join(forge_dir, 'TFC Resources'), '', zip)
    zip_end()
    
    zip_start('TFCraft-src.zip')
    zip_add('tfc_shared/src',     'src/minecraft')
    zip_add('patches',          'patches')
    zip_add('tfc_credits.txt')
    zip_add('install/install.cmd')
    zip_add('install/install.sh')
    zip_add('install/README.txt')
    zip_add('install/install.py')
    zip_add('tfc.py')
    zip_add('license.txt')
    zip_end()
    
    print '=================================== Release Finished %d =================================' % error_level
    sys.exit(error_level)
    
    
def zip_add(file, key=None):
    if key == None:
        key = os.path.basename(file)
    else:
        key = key.replace('/', os.sep)
    if not zip_base is None:
        key = os.path.join(zip_base, key)
    file = os.path.join(forge_dir, file.replace('/', os.sep))
    if os.path.isdir(file):
        zip_folder(file, key, zip)
    else:
        if os.path.isfile(file):
            print key
            zip.write(file, key)
    
def zip_start(name, base=None):
    global zip, zip_name, zip_base
    zip_name = name
    
    print '=================================== %s Start =================================' % zip_name
    zip_file = os.path.join(mcp_dir, 'TFC Build', name)
    zip = zipfile.ZipFile(zip_file, 'w', zipfile.ZIP_DEFLATED)
    zip_base = base
    
def zip_end():
    global zip, zip_name, zip_base
    zip.close()
    print '=================================== %s Finished =================================' % zip_name
    zip_name = None
    zip_base = None
    
if __name__ == '__main__':
    main()
