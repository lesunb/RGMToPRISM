// Generated from br/unb/cic/RTRegex.g4 by ANTLR 4.3
package br.unb.cic;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RTRegexParser}.
 */
public interface RTRegexListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code parens}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParens(@NotNull RTRegexParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParens(@NotNull RTRegexParser.ParensContext ctx);

	/**
	 * Enter a parse tree produced by the {@code blank}
	 * labeled alternative in {@link RTRegexParser#rt}.
	 * @param ctx the parse tree
	 */
	void enterBlank(@NotNull RTRegexParser.BlankContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blank}
	 * labeled alternative in {@link RTRegexParser#rt}.
	 * @param ctx the parse tree
	 */
	void exitBlank(@NotNull RTRegexParser.BlankContext ctx);

	/**
	 * Enter a parse tree produced by the {@code gId}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGId(@NotNull RTRegexParser.GIdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code gId}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGId(@NotNull RTRegexParser.GIdContext ctx);

	/**
	 * Enter a parse tree produced by the {@code gTry}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGTry(@NotNull RTRegexParser.GTryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code gTry}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGTry(@NotNull RTRegexParser.GTryContext ctx);

	/**
	 * Enter a parse tree produced by the {@code gSkip}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGSkip(@NotNull RTRegexParser.GSkipContext ctx);
	/**
	 * Exit a parse tree produced by the {@code gSkip}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGSkip(@NotNull RTRegexParser.GSkipContext ctx);

	/**
	 * Enter a parse tree produced by the {@code gTime}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGTime(@NotNull RTRegexParser.GTimeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code gTime}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGTime(@NotNull RTRegexParser.GTimeContext ctx);

	/**
	 * Enter a parse tree produced by the {@code gOpt}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGOpt(@NotNull RTRegexParser.GOptContext ctx);
	/**
	 * Exit a parse tree produced by the {@code gOpt}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGOpt(@NotNull RTRegexParser.GOptContext ctx);

	/**
	 * Enter a parse tree produced by the {@code gCard}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGCard(@NotNull RTRegexParser.GCardContext ctx);
	/**
	 * Exit a parse tree produced by the {@code gCard}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGCard(@NotNull RTRegexParser.GCardContext ctx);

	/**
	 * Enter a parse tree produced by the {@code gAlt}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGAlt(@NotNull RTRegexParser.GAltContext ctx);
	/**
	 * Exit a parse tree produced by the {@code gAlt}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGAlt(@NotNull RTRegexParser.GAltContext ctx);

	/**
	 * Enter a parse tree produced by the {@code printExpr}
	 * labeled alternative in {@link RTRegexParser#rt}.
	 * @param ctx the parse tree
	 */
	void enterPrintExpr(@NotNull RTRegexParser.PrintExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code printExpr}
	 * labeled alternative in {@link RTRegexParser#rt}.
	 * @param ctx the parse tree
	 */
	void exitPrintExpr(@NotNull RTRegexParser.PrintExprContext ctx);
}