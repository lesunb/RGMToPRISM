package $UTIL_PACKAGE.components;

import jadex.util.Tuple;

/**
 * A Tropos contribution link to a Softgoal.
 * Can define fixed values for the different contribution modes (++ to --)
 * 
 *  @author Mirko Morandini
 */
public class TContrib extends Tuple {
	public TContrib(String begin, String end, String contrib){
		super(new Object[]{begin, end, contrib});
	}

	public TSoftgoal getSoftgoal(){
		return Components.getSoftgoal((String)get(1));
	}
	public String getContrib(){
		return (String)get(2);
	}
	
	public double getContribValue(){
		double w=0;
		String cont=getContrib();
		if (cont.equals( "++" ) ) w=0.8;
		else if (cont.equals( "+" )) w=0.4;
		else if (cont.equals( "-" )) w=-0.4;
		else if (cont.equals( "--" ) ) w=-0.8;
		return w;
	}
	
	public double getContribRating(){
		double imp=getSoftgoal().getImportance();
		double cont=getContribValue();
		
		System.out.println("[TContrib]"+get(0)+" - "+ get(1) + ": "+cont+", w*imp = "+cont*imp);
		return cont*imp;
	}
}