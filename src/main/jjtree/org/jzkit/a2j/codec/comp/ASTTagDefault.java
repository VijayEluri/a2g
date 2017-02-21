/* Generated By:JJTree: Do not edit this line. ASTTagDefault.java */

package org.jzkit.a2j.codec.comp;

public class ASTTagDefault extends SimpleNode {
  public int which = 0;

  public ASTTagDefault(int id) {
    super(id);
  }

  public ASTTagDefault(AsnParser p, int id) {
    super(p, id);
  }

  public void pass1()
  {
    int i, k = jjtGetNumChildren();

    for (i = 0; i < k; i++)
    {
      Node o = jjtGetChild(i);
      o.pass1();
    }

    CodecBuilderInfo info = CodecBuilderInfo.getInfo();

    switch( which )
    {
      case 2:
        info.defaultTaggingIsImplicit();
        break;
      default:
      case 1:
        info.defaultTaggingIsExplicit();
        break;
    }
  
  }

}
