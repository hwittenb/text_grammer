package text_grammer;

import java.io.FileNotFoundException;


public class Parser
{
	private LexicalAnalyzer lex;

	public Parser(String fileName) throws FileNotFoundException, LexicalException
	{
		lex = new LexicalAnalyzer (fileName);
	}

	/*****************************************************
	 * implements the production <assign> -> <id> assign_op <expr>
	 */
	public Assignment parse() throws ParserException, LexicalException
	{
		Id var = getId();
		Token tok = lex.getNextToken();
		match (tok, TokenType.ASSIGN_TOK);
		Expression expr = getExpression();
		return new Assignment (var, expr);
	}

	/**************************************************************
	 * implements the production <expr> -> <operator> <expr> <expr> | id | constant
	 */
	private Expression getExpression() throws ParserException, LexicalException
	{
		Expression expr;
		Token tok = lex.getLookaheadToken ();
		if (tok.getTokType() == TokenType.ADD_TOK || tok.getTokType() == TokenType.MUL_TOK)
			expr = getBinaryExpression ();
		else if (tok.getTokType() == TokenType.ID_TOK)
			expr = getId();
		else
			expr = getConstant();
		return expr;
	}

	/****************************************************
	 * implements the production <expr> -> <operator> <expr> <expr>
	 */
	private Expression getBinaryExpression() throws ParserException, LexicalException
	{
		ArithmeticOperator op;
		Token tok = lex.getNextToken();
		if (tok.getTokType() == TokenType.ADD_TOK)
		{
			match (tok, TokenType.ADD_TOK);
			op = ArithmeticOperator.ADD_OP;
		}
		else if (tok.getTokType() == TokenType.MUL_TOK)
		{
			match (tok, TokenType.MUL_TOK);
			op = ArithmeticOperator.MUL_OP;
		}
		else
			throw new ParserException (" operator expected at row " +
					tok.getRowNumber() +" and column "  + tok.getColumnNumber());
		Expression expr1 = getExpression();
		Expression expr2 = getExpression();
		return new BinaryExpression (op, expr1, expr2);
	}

	private Id getId() throws LexicalException, ParserException
	{
		Token tok = lex.getNextToken();
		match (tok, TokenType.ID_TOK);
		return new Id (tok.getLexeme().charAt(0));
	}

	private Expression getConstant() throws ParserException, LexicalException
	{
		Token tok = lex.getNextToken();
		match (tok, TokenType.CONST_TOK);
		int value = Integer.parseInt(tok.getLexeme());
		return new Constant (value);
	}

	private void match(Token tok, TokenType tokType) throws ParserException
	{
		assert (tok != null && tokType != null);
		if (tok.getTokType() != tokType)
			throw new ParserException (tokType.name() + " expected at row " +
					tok.getRowNumber() +" and column "  + tok.getColumnNumber());
	}

}
