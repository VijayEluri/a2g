/* Generated By:JJTree: Do not edit this line. ASTSymbolsFromModule.java */

package org.jzkit.a2j.codec.comp;

import java.util.logging.*;
                                                                                                                                        
public class ASTSymbolsFromModule extends SimpleNode {

  private static Logger log = Logger.getLogger(ASTSymbolsFromModule.class.getName());
                                                                                                                                        
  public ASTSymbolsFromModule(int id) {
    super(id);
  }

  public ASTSymbolsFromModule(AsnParser p, int id) {
    super(p, id);
  }

  public void pass1()
  {
    ASTModuleIdentifier mi = (ASTModuleIdentifier)jjtGetChild(1);
    CodecBuilderInfo info = CodecBuilderInfo.getInfo();
    // String basepkg = System.getProperty("CodecBasePackage");

    log.fine("ASTSymbolsFromModule from "+mi.getModuleReference().module_ref);

    ASTSymbolList sl = (ASTSymbolList)jjtGetChild(0);

    for (int j=0;j<sl.jjtGetNumChildren();j++)
    {
        ASTSymbol s = (ASTSymbol)sl.jjtGetChild(j);
        if ( s.which == 1 )
        {
          info.registerImport(mi.getModuleReference().module_ref, ((ASTtypereference)(s.jjtGetChild(0))).typeref);
        }
        else
        {
          log.log(Level.WARNING,"Symbol which = "+s.which);
        }
    }
  }
}
