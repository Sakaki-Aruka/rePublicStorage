# commands for administrators (or operators).  
/storage modify [operation] [amount/operation(ignore)] [target]  
- [operation]  
  - addition : To add [amount] to [target] item amount.
  - subtraction : To subtraction [amount] from [target] item amount.
  - multi : [target] item amount multiplied by [amount].
  - division : [target] item amount divided by [amount].
  - ignore : To add [target] item to the ignore items list.
- [amount/operation(ignore)]
  - amount 
    - the number of to use in operation.
  - operation(ignore)
    - add : To add [target] to the ignore item list.
    - remove : To remove [target] from the ignore item list.
- [target]
  - target items id (in UPPERCASE)
