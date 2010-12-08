/* Generated By:JJTree: Do not edit this line. ASTSetOrSequenceType.java */

package org.jzkit.a2j.codec.comp;

import java.io.File;
import java.io.Writer;
import java.io.StringWriter;
import java.io.FileWriter;
import java.util.logging.*;

public class ASTSetOrSequenceType extends SimpleNode {

  private Logger log = Logger.getLogger(this.getClass().getName());

  public int which = 0;

  public ASTSetOrSequenceType(int id) {
    super(id);
  }

  public ASTSetOrSequenceType(AsnParser p, int id) {
    super(p, id);
  }

  public void getSequenceMembers(SequenceTypeInfo sti)
  {
    CodecBuilderInfo info = CodecBuilderInfo.getInfo();
                                                                           
    log.fine("ASTSetOrSequenceType::getSequenceMembers");
    ASTElementTypeList choice_elements = (ASTElementTypeList)jjtGetChild(0);
    int i, k = choice_elements.jjtGetNumChildren();

    for (i = 0; i < k; i++)
    {
      ASTElementType choice_element = (ASTElementType)(choice_elements.jjtGetChild(i));
      if ( choice_element.which == 1 )
      {
        ASTNamedType name = (ASTNamedType)(choice_element.jjtGetChild(0));
        String element_name = name.getName().replace ( '-', '_' );
        // String element_name = name.getName();
        ASTType element_type_info = name.getType();

        Object default_value = null;
        if ( choice_element.has_default ) {
          // System.out.println("Has default");
          // This element has a default value. Pull off the NamedValue component.
          ASTNamedValue named_value = (ASTNamedValue)(choice_element.jjtGetChild(1));

          // Now we need to know of the named_value has an [ identifier() ] or just a value()
          ASTValue value = null;

          // System.out.println("Processing "+named_value.jjtGetNumChildren()+" children");
          if ( named_value.jjtGetNumChildren() == 2 ) {
            // Deal with identifier() value()
            // System.out.println("identifier");
            value = (ASTValue)(named_value.jjtGetChild(1));
          }
          else if ( named_value.jjtGetNumChildren() == 1 ) {
            // just value();
            // System.out.println("just value");
            value = (ASTValue)(named_value.jjtGetChild(0));
          }

          if ( ( value != null ) && ( value.jjtGetChild(0) instanceof ASTBuiltinValue ) ) {
            ASTBuiltinValue biv = (ASTBuiltinValue) value.jjtGetChild(0);

            // It's a builtin value... DEAL WITH IT :)
            // System.out.println("Default value for builtin type "+biv.which);
            switch ( biv.which ) {
              case 0:
                // Boolean
                // System.out.println("Boolean: "+((ASTBooleanValue)biv.jjtGetChild(0)).value);
                default_value = ((ASTBooleanValue)biv.jjtGetChild(0)).value;
                break;
              case 3:
                String tmp_num = ((ASTSignedNumber)biv.jjtGetChild(0)).number;
                // System.out.println("SignedNumber: "+tmp_num);
                default_value = tmp_num;
                break;
              default:
                System.out.println("unhandled default");
            }
            // System.out.println("Default: "+default_value);
          }
          else {
            System.out.println("value="+value+" or getChild not instance of ASTBuiltinValue");
          }
        }

        boolean has_tagging = false;
        int tag_class = -1;
        int tag_number = -1;
        boolean is_implicit = ( info.default_tagging_is_explicit == true ? false : true );                                                        

        // Find out if the type is tagged
        if ( element_type_info.which == 1 ) // It's a builtin type
        {
          ASTBuiltinType bit = (ASTBuiltinType) (element_type_info.jjtGetChild(0));
 
          if ( bit.which == 6 )
          {
            has_tagging = true;
            // It's a tagged type, extract tagging information and proceed
            // with the actual type info...
            ASTTaggedType tt = (ASTTaggedType) (bit.jjtGetChild(0));
            is_implicit = tt.isImplicit();

            // The real type we want to process is inside the tagging info
            element_type_info = tt.getType();

            // Extract the tagging info
            ASTTag tag = tt.getTag();
            if ( tag.hasTagClass )
              tag_class = tag.getTagClass().tag_class;
            else
              tag_class = 0x80; // Assume context tag class if none given
   
            ASTClassNumber cn = tag.getClassNumber();
   
            if ( cn.which == 1 )
            {
              // It's a number
              tag_number = cn.getNumber().getNumber().intValue();
            }
            else
            {
              log.log(Level.WARNING,"Unhandled tag number type");
              System.exit(0);
              // LATER: Should throw an exception here
            }
          }
        }

        String type_name = element_type_info.getTypeName();

        // If it's a tagged built in type, just check some specials
        if ( element_type_info.which == 1 ) // It's a builtin type
        {
          ASTBuiltinType bit = (ASTBuiltinType) (element_type_info.jjtGetChild(0));
          switch ( bit.which )
          {
            case 2:  // SetOrSequence
              type_name=element_name+"_inline"+info.getNextInlineCounter();
              info.createTypeInfoFor(type_name, element_type_info);
              info.createTypeInfoFor(type_name, element_type_info);
              break;

            case 3:  // SetOrSequenceOf
              type_name=element_name+"_inline"+info.getNextInlineCounter();
              info.createTypeInfoFor(type_name, element_type_info);
              break;

            case 4:  // Choice
              type_name=element_name+"_inline"+info.getNextInlineCounter();
              info.createTypeInfoFor(type_name, element_type_info);
              break;

            default:
              // This is not fatal.. it's just not a special case!
              // log.log(Level.SEVERE,"Unhandled internal type "+bit.which+" "+type_name+" in Set or Sequence ("+element_name+")");
              break;
          }
        }

        sti.registerTaggedMember(element_name,
                                 tag_class,
                                 tag_number,
                                 is_implicit,
                                 type_name,
                                 choice_element.optional,
                                 default_value);

        log.fine("Adding Sequence element "+tag_class+
                           " "+tag_number+" "+is_implicit+
                           " "+element_name+
                           " "+ type_name + ( choice_element.optional == true ? " OPTIONAL " : "" ) );

      }
    }
  }

  // public String getBaseClassName(String element_name)
  // {
  //   return element_name+"_codec ";
  // }
}
