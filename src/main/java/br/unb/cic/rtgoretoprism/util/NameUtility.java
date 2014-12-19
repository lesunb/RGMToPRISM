/**
 * <copyright>
 *
 * TAOM4E - Tool for Agent Oriented Modeling for the Eclipse Platform
 * Copyright (C) ITC-IRST, Trento, Italy
 * Authors: Davide Bertolini, Aliaksei Novikau
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * The electronic copy of the license can be found here:
 * http://sra.itc.it/tools/taom/freesoftware/gpl.txt
 *
 * The contact information:
 * e-mail: taom4e@itc.it
 * site: http://sra.itc.it/tools/taom4e/
 *
 * </copyright>
 */

package br.unb.cic.rtgoretoprism.util;

/**
 * This class expose some utility (actually just one) in order to assure that
 * the used element name are valide 'Java' one too.
 *  
 * @author bertolini
 */
public class NameUtility {
	/**
	 * TODO: this should be moved into the 2 qvt transformations (targeting
	 * last Tefkat release):
	 * - class, action, interaction
	 * - targetFileName, Agentj name/role, Behaviour link/sender/receiver  
	 */
	
    /**
     * Remove some 'special' character from the source String object giving a 'normalized' one.
     * 
     * @param source
     * @return
     */
    public static  String adjustName( String source ) {
    	//A line terminator is a one- or two-character sequence that marks the end of a line of 
    	//the input character sequence. The following are recognized as line terminators: 
    		//A newline (line feed) character ('\n'), 
    		//A carriage-return character followed immediately by a newline character ("\r\n"), 
    		//A standalone carriage-return character ('\r'), 
    		//A next-line character ('\u0085'), 
    		//A line-separator character ('\u2028'), or 
    		//A paragraph-separator character ('\u2029).
    	
    	//\t	The tab character ('\u0009')	
/*    	//\n	The newline (line feed) character ('\u000A')	
    	//\r	The carriage-return character ('\u000D') */
    	
    	//the space character
    	
    	return source.replaceAll( "(\r\n)|[\t\n\r\u0085\u2028\u2029 ]", "_" );
    	//\s	A whitespace character: [ \t\n\x0B\f\r]
    }
}