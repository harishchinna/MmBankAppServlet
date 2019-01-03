package com.capgemini.MmBankWbProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.service.SavingsAccountService;
import com.moneymoney.account.service.SavingsAccountServiceImpl;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;

@WebServlet("*.mm")
public class MmBankController extends HttpServlet {
	boolean flag = false;
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher;

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/bankapp_db", "root", "root");
			PreparedStatement preparedStatement = connection
					.prepareStatement("DELETE FROM ACCOUNT");
			preparedStatement.execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		super.init(config);
	}

	public MmBankController() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		SavingsAccountService savingsAccountService = new SavingsAccountServiceImpl();
		SavingsAccount savingsAccount = null;
		SavingsAccount savingsAccountOne = null;

		String path = request.getServletPath();
		System.out.println(path);
		switch (path) {
		case "/addNewAccount.mm":
			response.sendRedirect("AddNewAccountForm.jsp");
			break;
		case "/addNewAccountForm.mm":
			String accountHolder = request.getParameter("accountHolderName");
			double accountBalance = Double.parseDouble(request
					.getParameter("InitialBalance"));
			boolean salary = request.getParameter("salaryType")
					.equalsIgnoreCase("Yes") ? true : false;

			try {
				savingsAccountService.createNewAccount(accountHolder,
						accountBalance, salary);
				response.sendRedirect("getAll.mm");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		case "/updateAccount.mm":
			response.sendRedirect("UpdateForm.html");
			break;
		case "/updateForm.mm":
			int accountBal = Integer.parseInt(request
					.getParameter("accountnumber"));
			try {
				SavingsAccount accountUpdate = savingsAccountService
						.getAccountById(accountBal);
				request.setAttribute("accounts", accountUpdate);
				dispatcher = request.getRequestDispatcher("updateAccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case "/updateAccountDetails.mm":
			int accountId = Integer.parseInt(request.getParameter("txtNumber"));
			SavingsAccount accountUpdate;
			try {
				accountUpdate = savingsAccountService.getAccountById(accountId);
				String accHName = request.getParameter("txtAccHn");
				accountUpdate.getBankAccount().setAccountHolderName(accHName);
				double accBal = Double.parseDouble(request.getParameter("txtBal"));
				boolean isSalary = request.getParameter("rdSal").equalsIgnoreCase("no")?false:true;
				accountUpdate.setSalary(isSalary);
				savingsAccountService.updateAccount(accountUpdate);
				response.sendRedirect("getAll.mm");
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;

		case "/closeAccount.mm":
			response.sendRedirect("CloseAccountForm.jsp");
			break;
		case "/closeAccountForm.mm":
			int accountNumber = Integer.parseInt(request
					.getParameter("accountId"));
			try {
				savingsAccountService.deleteAccount(accountNumber);
				response.sendRedirect("getAll.mm");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case "/getCurrentBalance.mm":
			response.sendRedirect("CurrentBalanceForm.jsp");
			break;
		case "/currentBalanceForm.mm":
			int currentBal = Integer.parseInt(request
					.getParameter("accountNumber"));

			try {
				double currentBalance = savingsAccountService
						.checkAccountBalance(currentBal);
				PrintWriter out = response.getWriter();
				out.println("Your Current Balance is:");
				out.println(currentBalance);

			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;

		case "/withdraw.mm":
			response.sendRedirect("Withdraw.jsp");
			break;
		case "/withdrawForm.mm":
			int accountnumber = Integer.parseInt(request
					.getParameter("accountnumber"));
			int amountToWithDraw = Integer.parseInt(request
					.getParameter("amount"));
			try {
				savingsAccount = savingsAccountService
						.getAccountById(accountnumber);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			try {
				savingsAccountService
						.withdraw(savingsAccount, amountToWithDraw);
				DBUtil.commit();

				double currentbal = savingsAccountService
						.checkAccountBalance(accountnumber);
				PrintWriter out = response.getWriter();
				out.println("Your Current Balance is:");
				out.println(currentbal);
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

			break;
		case "/deposit.mm":
			response.sendRedirect("Deposit.jsp");
			break;
		case "/depositForm.mm":
			int accountNumberToDeposit = Integer.parseInt(request
					.getParameter("accountnumber"));
			int amountToDeposit = Integer.parseInt(request
					.getParameter("amount"));

			try {
				savingsAccount = savingsAccountService
						.getAccountById(accountNumberToDeposit);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}

			try {
				savingsAccountService.deposit(savingsAccount, amountToDeposit);
				DBUtil.commit();
				double currentbal = savingsAccountService
						.checkAccountBalance(accountNumberToDeposit);

				PrintWriter out = response.getWriter();
				out.println("Your Current Balance is:");
				out.println(currentbal);
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

			break;
		case "/fundTransfer.mm":
			response.sendRedirect("FundTransfer.jsp");
			break;
		case "/fundTransferForm.mm":
			int accountNumberToWithdraw = Integer.parseInt(request
					.getParameter("accountnumberofwithdraw"));
			int accountNumberToDepositamount = Integer.parseInt(request
					.getParameter("accountnumberofdeposit"));
			int amountToTransfer = Integer.parseInt(request
					.getParameter("amount"));
			try {
				savingsAccount = savingsAccountService
						.getAccountById(accountNumberToWithdraw);
				savingsAccountOne = savingsAccountService
						.getAccountById(accountNumberToDepositamount);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}

			try {
				savingsAccountService.fundTransfer(savingsAccount,
						savingsAccountOne, amountToTransfer);
				DBUtil.commit();
				double currentbal;

				currentbal = savingsAccountService
						.checkAccountBalance(accountNumberToWithdraw);
				PrintWriter out = response.getWriter();
				out.println("<h1>Funds Transfered Successfully</h1>");
				out.println("Your Current Balance is:");
				out.println(currentbal);
			} catch (AccountNotFoundException e) {
				e.printStackTrace();
			}

			catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/searchForm.mm":
			response.sendRedirect("SearchForm.jsp");
			break;
		case "/search.mm":
			int accountNumberToSearch = Integer.parseInt(request
					.getParameter("txtAccountNumber"));
			try {
				SavingsAccount account = savingsAccountService
						.getAccountById(accountNumberToSearch);
				request.setAttribute("account", account);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case "/getAll.mm":
			try {
				List<SavingsAccount> accounts = savingsAccountService
						.getAllSavingsAccount();
				request.setAttribute("accounts", accounts);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/sortByName.mm":
			flag = !flag;
			try {
				Collection<SavingsAccount> accounts = savingsAccountService
						.getAllSavingsAccount();
				List<SavingsAccount> accountSet = new ArrayList<>(accounts);
				Collections.sort(accountSet, new Comparator<SavingsAccount>() {
					@Override
					public int compare(SavingsAccount arg0, SavingsAccount arg1) {
						int result = arg0
								.getBankAccount()
								.getAccountHolderName()
								.compareTo(
										arg1.getBankAccount()
												.getAccountHolderName());
						if (flag == true) {
							return result;
						}
						return -result;
					}
				});

				request.setAttribute("accounts", accountSet);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

			break;
		case "/sortByBalance.mm":
			flag = !flag;
			try {
				Collection<SavingsAccount> accounts = savingsAccountService
						.getAllSavingsAccount();
				List<SavingsAccount> accountSet = new ArrayList<>(accounts);
				Collections.sort(accountSet, new Comparator<SavingsAccount>() {
					@Override
					public int compare(SavingsAccount arg0, SavingsAccount arg1) {
						int result = (int) (arg0.getBankAccount()
								.getAccountBalance() - (arg1.getBankAccount()
								.getAccountBalance()));
						if (flag == true) {
							return result;
						}
						return -result;
					}
				});

				request.setAttribute("accounts", accountSet);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case "/sortBySalaryType":
			flag = !flag;
			try {
				Collection<SavingsAccount> accounts = savingsAccountService
						.getAllSavingsAccount();
				List<SavingsAccount> accountSet = new ArrayList<>(accounts);
				Collections.sort(accountSet, new Comparator<SavingsAccount>() {
					@Override
					public int compare(SavingsAccount arg0, SavingsAccount arg1) {
						int result = Boolean.compare(arg0.isSalary(),
								arg1.isSalary());
						if (flag == true) {
							return result;
						}
						return -result;
					}
				});

				request.setAttribute("accounts", accountSet);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
