package testdestroyer;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class SearchAction implements IEditorActionDelegate {

	private IEditorPart targetEditer;
	
	@Override
	public void run(IAction action) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		MessageDialog.openInformation(
				workbench.getActiveWorkbenchWindow().getShell(),
				"TestDestory",
				"成功！");
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
