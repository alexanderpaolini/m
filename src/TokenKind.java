public enum TokenKind {
    // Logical
    DOUBLE_AND, // &&
    DOUBLE_PIPE, // ||
    BANG, // !
    PIPE, // |
    LEFT_ARROW, // <
    RIGHT_ARROW, // >
    EQUALS, // =
    DOUBLE_EQUALS, // ==
    LEFT_ARROW_EQUALS, // <=
    RIGHT_ARROW_EQUALS, // >=
    BANG_EQUALS, // !=
    // Mathematics
    STAR, // *
    PLUS, // +
    DASH, // -
    SLASH, // /
    DOUBLE_SLASH, // //
    PERCENT, // %
    // Keywords
    WHILE, // while(...)
    FOR, // for(...)
    IF, // if(...)
    ELSE, // else(...)
    NAMESPACE, 
    /* 
    namespace ... {
        group{
            ...
            function();
            ...
        }
    } 
    */
    ALIAS, // alias ... namespace::group::function
    INCLUDES, // !include "namespace/group.m";
    // Other..
    NUMERAL, // NUMERAL(x); x = 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, etc.
    LEFT_BRACKET, // [
    RIGHT_BRACKET, // ]
    LEFT_PAREN, // (
    RIGHT_PAREN, // )
    LEFT_BRACE, // {
    RIGHT_BRACE, // }
    COLON, // :
    SEMICOLON, // ;
    DOUBLE_QUOTE, // "
    SINGLE_QUOTE, // '
    HASH, // #
    COMMA, // ,
    UNDERSCORE, // _
    END_OF_FILE,
    INVALID_TOKEN,
}
