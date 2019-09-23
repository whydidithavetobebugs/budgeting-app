# Budget App

## About

This is a simple app to track transactions against a daily budget. The budgeting rules are as follows:

1. In the Settings page, set your daily budget. The default is £18.
2. In the Main page, record any transactions made by submitting the amount. Round to the nearest integer.
3. The Main page also displays your current total. This may either be displayed as "the amount you currently have to spend" (in the case of a surplus), or "the amount you are over budget" (in the case of a deficit).
4. The current total is updated for each new transaction submitted.
5. The current total is also updated each day with the daily budget.
6. You can never have a surplus greater than your daily budget. If you finish a day with a surplus, the next day the daily budget will be applied again, but the current total will only be updated to the daily budget. Essentially surpluses are banked, and add to your savings.
7. Deficits however are tracked until they are wiped out and you run a surplus again. Deficits are reduced by the daily budget.

As an example (assuming the default daily budget):

1. On Day 1, you start with a daily budget of £18 to spend.
2. During Day 1, you spend £15, leaving you with £3 to spend at the end of the day.
3. At the start of Day 2, your daily budget is applied, and you again have £18 to spend (as you can't have more than the daily budget to spend).
4. During Day 2, you spend £29, leaving you with a deficit of £11 at the end of the day.
5. At the start of Day 3, the daily budget (£18) is again applied, but £11 of that is used to clear your deficit, so you only have £7 to spend.
6. During Day 3, you spend £12, leaving you with a deficit of £5.
7. At the start of Day 4, the daily budget is applied, leaving you with £13 to spend.
8. During Day 4, you spend £11, leaving you with £2 to spend.
9. At the start of Day 5, you have £18 to spend.

## TODO

- Move budgeting calculation logic to com.whydidithavetobebugs.budgetapp.engine.BudgetEngine
- Add tests
- Package app
- Show history of transactions somewhere in app (amount and timestamp)
- Allow a transaction to be edited to include description
- Store transactions on GCP
- Allow different currencies (should be an option on Settings page)
- New button to add more budgets (e.g. one for food, one for travel). These budgets should be tabbed or selectable from a menu, and should have different names. They should also have different daily budgets.
