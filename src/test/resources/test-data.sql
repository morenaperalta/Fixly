DELETE FROM attachments;
DELETE FROM comments;
DELETE FROM status_history;
DELETE FROM workorder_assigned_to;
DELETE FROM workorder;
DELETE FROM users;
-- Passwords used (bcrypt):
-- Admin123## | Client123## | Tech123## | Super123##

-- Users with different roles
INSERT INTO users (id, username, email, password, first_name, last_name, company, role, created_at, updated_at) VALUES
  -- Admin user
  (1, 'admin', 'admin@fixly.com', '$2a$10$w/bVeiPX2eRA.2bFTzBLZ.Xg0rEYhvMfFYmM2iicmRgbJGwt2HSfC', 'Sarah', 'Johnson', 'Fixly Corp', 'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

  -- Client users
  (2, 'client', 'anna.smith@company1.com', '$2a$10$WKvxmGy26lwnBdo0Sr5ySub8QZ.lfbYbYcKGhfM0Jpj736h9JqvfG', 'Anna', 'Smith', 'Tech Solutions Inc', 'CLIENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (3, 'client2', 'mike.davis@company2.com', '$2a$10$WKvxmGy26lwnBdo0Sr5ySub8QZ.lfbYbYcKGhfM0Jpj736h9JqvfG', 'Mike', 'Davis', 'Global Manufacturing Corp', 'CLIENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (4, 'client3', 'lisa.brown@company3.com', '$2a$10$WKvxmGy26lwnBdo0Sr5ySub8QZ.lfbYbYcKGhfM0Jpj736h9JqvfG', 'Lisa', 'Brown', 'Retail Systems Ltd', 'CLIENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

  -- Technician users
  (5, 'technician', 'louis.garcia@fixly.com', '$2a$10$TkLMl//9S4uJ.rWkhLeCSefvOVyg7MDxumJWh/ijrR8LCo8fbfXBe', 'Louis', 'Garcia', 'Fixly Corp', 'TECHNICIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (6, 'tech2', 'carlos.martinez@fixly.com', '$2a$10$TkLMl//9S4uJ.rWkhLeCSefvOVyg7MDxumJWh/ijrR8LCo8fbfXBe', 'Carlos', 'Martinez', 'Fixly Corp', 'TECHNICIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (7, 'tech3', 'emma.wilson@fixly.com', '$2a$10$TkLMl//9S4uJ.rWkhLeCSefvOVyg7MDxumJWh/ijrR8LCo8fbfXBe', 'Emma', 'Wilson', 'Fixly Corp', 'TECHNICIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (8, 'tech4', 'james.taylor@fixly.com', '$2a$10$TkLMl//9S4uJ.rWkhLeCSefvOVyg7MDxumJWh/ijrR8LCo8fbfXBe', 'James', 'Taylor', 'Fixly Corp', 'TECHNICIAN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

  -- Supervisor users
  (9, 'supervisor', 'martha.rodriguez@fixly.com', '$2a$10$A99XRsJgEk3a3iy6Fethrek9IWkEZZQkVb9WfVINvGdpIXv1SANiO', 'Martha', 'Rodriguez', 'Fixly Corp', 'SUPERVISOR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (10, 'supervisor2', 'robert.anderson@fixly.com', '$2a$10$A99XRsJgEk3a3iy6Fethrek9IWkEZZQkVb9WfVINvGdpIXv1SANiO', 'Robert', 'Anderson', 'Fixly Corp', 'SUPERVISOR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- WorkOrders with different statuses and priorities
INSERT INTO workorder (id, identifier, title, description, status, priority, supervision_status, location, created_at, updated_at, created_by, supervised_by) VALUES
  -- PENDING work orders
  (1, 'WO-123456-082025', 'Air conditioning system repair', 'The air conditioning unit in the main office is not cooling properly.', 'PENDING', 'HIGH', 'PENDING', 'Main Office Building A - Floor 2', DATEADD(DAY, -2, CURRENT_TIMESTAMP), DATEADD(DAY, -2, CURRENT_TIMESTAMP), 2, 9),
  (2, 'WO-234567-082025', 'Replace broken window', 'Large crack in conference room window needs immediate replacement.', 'PENDING', 'MEDIUM', 'PENDING', 'Conference Room 201 - Building B', DATEADD(DAY, -1, CURRENT_TIMESTAMP), DATEADD(DAY, -1, CURRENT_TIMESTAMP), 3, 9),
  (3, 'WO-345678-082025', 'Server room ventilation check', 'Server room temperature monitoring shows irregular patterns.', 'PENDING', 'CRITICAL', 'PENDING', 'Server Room - Basement Level', DATEADD(HOUR, -3, CURRENT_TIMESTAMP), DATEADD(HOUR, -3, CURRENT_TIMESTAMP), 4, 10),

  -- ASSIGNED work orders
  (4, 'WO-456789-082025', 'Replace fluorescent light fixtures', 'Multiple fluorescent lights flickering in warehouse section C.', 'ASSIGNED', 'MEDIUM', 'PENDING', 'Warehouse Section C - Rows 15-20', DATEADD(DAY, -1, CURRENT_TIMESTAMP), DATEADD(HOUR, -6, CURRENT_TIMESTAMP), 2, 9),
  (5, 'WO-567890-082025', 'Repair loading dock mechanism', 'Loading dock door #3 is stuck in half-open position.', 'ASSIGNED', 'HIGH', 'PENDING', 'Loading Dock Area - Door #3', DATEADD(HOUR, -8, CURRENT_TIMESTAMP), DATEADD(HOUR, -4, CURRENT_TIMESTAMP), 3, 10),

  -- IN_PROGRESS work orders
  (6, 'WO-678901-082025', 'Industrial printer maintenance', 'Quarterly maintenance on industrial label printer.', 'IN_PROGRESS', 'LOW', 'APPROVED', 'Production Floor - Station 12', DATEADD(DAY, -2, CURRENT_TIMESTAMP), DATEADD(HOUR, -2, CURRENT_TIMESTAMP), 4, 9),
  (7, 'WO-789012-082025', 'Emergency fire alarm system check', 'Fire alarm panel showing fault code.', 'IN_PROGRESS', 'CRITICAL', 'APPROVED', 'Security Office - Fire Control Panel', DATEADD(HOUR, -5, CURRENT_TIMESTAMP), DATEADD(HOUR, -1, CURRENT_TIMESTAMP), 2, 10),
  (8, 'WO-890123-082025', 'Repair office chairs', 'Five executive office chairs have broken hydraulic cylinders.', 'IN_PROGRESS', 'LOW', 'APPROVED', 'Executive Offices - Floor 3', DATEADD(DAY, -1, CURRENT_TIMESTAMP), DATEADD(MINUTE, -30, CURRENT_TIMESTAMP), 3, 9),

  -- READY_FOR_REVIEW work orders
  (9, 'WO-901234-082025', 'Install new security cameras', 'Install 4 new IP security cameras in parking lot area.', 'READY_FOR_REVIEW', 'MEDIUM', 'PENDING', 'Employee Parking Lot - North Side', DATEADD(DAY, -3, CURRENT_TIMESTAMP), DATEADD(HOUR, -1, CURRENT_TIMESTAMP), 4, 10),
  (10, 'WO-012345-082025', 'Plumbing leak repair', 'Water leak detected under sink in employee break room.', 'READY_FOR_REVIEW', 'HIGH', 'CHANGES_REQUIRED', 'Employee Break Room - Building A', DATEADD(DAY, -2, CURRENT_TIMESTAMP), DATEADD(HOUR, -3, CURRENT_TIMESTAMP), 2, 9),
  (11, 'WO-123450-082025', 'HVAC filter replacement', 'Replace all HVAC filters in building C.', 'READY_FOR_REVIEW', 'LOW', 'PENDING', 'Building C - All HVAC Units', DATEADD(DAY, -1, CURRENT_TIMESTAMP), DATEADD(HOUR, -2, CURRENT_TIMESTAMP), 3, 10),

  -- CLOSED work orders
  (12, 'WO-234561-082025', 'Network cable replacement', 'Replace damaged ethernet cables in IT department.', 'CLOSED', 'MEDIUM', 'APPROVED', 'IT Department - Workstations 1-8', DATEADD(DAY, -5, CURRENT_TIMESTAMP), DATEADD(DAY, -1, CURRENT_TIMESTAMP), 4, 9),
  (13, 'WO-345672-082025', 'Paint warehouse walls', 'Repaint warehouse walls in sections A and B.', 'CLOSED', 'LOW', 'APPROVED', 'Warehouse Sections A & B', DATEADD(DAY, -7, CURRENT_TIMESTAMP), DATEADD(DAY, -2, CURRENT_TIMESTAMP), 2, 10),
  (14, 'WO-456783-082025', 'Replace broken door handle', 'Main entrance door handle is loose and needs replacement.', 'CLOSED', 'HIGH', 'APPROVED', 'Main Entrance - Building A', DATEADD(DAY, -4, CURRENT_TIMESTAMP), DATEADD(DAY, -1, CURRENT_TIMESTAMP), 3, 9);

-- Technician assignments
INSERT INTO workorder_assigned_to (workorder_id, user_id) VALUES
  (4, 5),
  (5, 6),
  (6, 7),
  (7, 5),
  (8, 8),
  (9, 6),
  (10, 7),
  (11, 8),
  (12, 5),
  (13, 6),
  (13, 7),
  (14, 8);

-- Comments
INSERT INTO comments (id, content, comment_type, created_at, author, workorder_id) VALUES
  (1, 'Initial assessment completed. AC unit requires professional HVAC technician.', 'INTERNAL', DATEADD(DAY, -1, CURRENT_TIMESTAMP), 9, 1),
  (2, 'Client reported temperature issues started 3 days ago.', 'CLIENT_NOTE', DATEADD(DAY, -1, CURRENT_TIMESTAMP), 2, 1),
  (3, 'Window damage appears to be caused by thermal expansion.', 'INTERNAL', DATEADD(HOUR, -12, CURRENT_TIMESTAMP), 9, 2),

  (4, 'Technician dispatched to warehouse. Estimated completion time: 4 hours.', 'INTERNAL', DATEADD(HOUR, -4, CURRENT_TIMESTAMP), 9, 4),
  (5, 'Client requested work to be completed after 6 PM.', 'CLIENT_NOTE', DATEADD(HOUR, -6, CURRENT_TIMESTAMP), 3, 5),
  (6, 'Loading dock mechanism parts ordered.', 'INTERNAL', DATEADD(HOUR, -2, CURRENT_TIMESTAMP), 6, 5),

  (7, 'Maintenance 60% complete. Printer head cleaned.', 'INTERNAL', DATEADD(HOUR, -3, CURRENT_TIMESTAMP), 7, 6),
  (8, 'Technician reported alarm panel issue persists.', 'CLIENT_NOTE', DATEADD(HOUR, -2, CURRENT_TIMESTAMP), 5, 7),
  (9, 'Chairs repaired. Awaiting supervisor approval.', 'INTERNAL', DATEADD(HOUR, -1, CURRENT_TIMESTAMP), 8, 8);
