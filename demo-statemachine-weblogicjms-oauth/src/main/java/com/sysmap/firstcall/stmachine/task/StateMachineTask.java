package com.sysmap.firstcall.stmachine.task;

import com.sysmap.firstcall.model.Ordem;

public interface StateMachineTask {

	void execute(Ordem ordem) throws Exception;
}
