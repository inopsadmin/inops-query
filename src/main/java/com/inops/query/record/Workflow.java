package com.inops.query.record;

import java.util.List;

import org.bson.types.ObjectId;

public record Workflow(
		String _id,
		String name,
		String initialState,
		List<String> states,
		List<Transition> transitions
) {
	public record Transition(
			String sourceState,
			String targetState,
			String event,
			String action,
			String guard
	) {}
}
