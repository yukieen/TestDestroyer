package testdestroyer;

import java.util.Set;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class SearchAction implements IEditorActionDelegate {

	private IEditorPart targetEditer;
	
	@Override
	public void run(IAction action) {
		IJavaElement je = JavaUI.getEditorInputJavaElement(this.targetEditer.getEditorInput());
		Product product = new Product((ICompilationUnit)je);
		Set<IMethod> testableMethods = product.getTestableMethods();
		Testers testers = new Testers(testableMethods);
		Set<IMethod> untested = testers.getUntested();
		
		StringBuilder message = new StringBuilder();
		for (IMethod m : untested) {
			message
				.append(getMethodFullName(m))
				.append(System.getProperty("line.separator"));
		}
		showMessageDialog("調査報告", message);
	}
	
	private static String getMethodFullName(IMethod method) {
		try {
			return Signature.toString(
						method.getSignature(),
						method.getElementName(),
						method.getParameterNames(),
						false,
						true
					) + " " + method.getSourceRange().getLength() + "m";
		} catch (JavaModelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	

	private void showMessageDialog(CharSequence title, CharSequence message) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		MessageDialog.openInformation(workbench.getActiveWorkbenchWindow().getShell(), title.toString(), message.toString());
	}
		 
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		this.targetEditer = targetEditor;
	}

}
