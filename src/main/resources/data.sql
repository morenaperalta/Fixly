-- Passwords used (bcrypt):
-- Admin123## | Client123## | Tech123## | Super123##

-- Users with different roles
INSERT INTO users (id, username, email, password, first_name, last_name, company, role, created_at, updated_at) VALUES
  -- Admin user
  (1, 'admin', 'admin@fixly.com', '$2a$10$w/bVeiPX2eRA.2bFTzBLZ.Xg0rEYhvMfFYmM2iicmRgbJGwt2HSfC', 'Sarah', 'Johnson', 'Fixly Corp', 'ADMIN', NOW(), NOW()),

  -- Client users
  (2, 'client', 'anna.smith@company1.com', '$2a$10$WKvxmGy26lwnBdo0Sr5ySub8QZ.lfbYbYcKGhfM0Jpj736h9JqvfG', 'Anna', 'Smith', 'Tech Solutions Inc', 'CLIENT', NOW(), NOW()),
  (3, 'client2', 'mike.davis@company2.com', '$2a$10$WKvxmGy26lwnBdo0Sr5ySub8QZ.lfbYbYcKGhfM0Jpj736h9JqvfG', 'Mike', 'Davis', 'Global Manufacturing Corp', 'CLIENT', NOW(), NOW()),
  (4, 'client3', 'lisa.brown@company3.com', '$2a$10$WKvxmGy26lwnBdo0Sr5ySub8QZ.lfbYbYcKGhfM0Jpj736h9JqvfG', 'Lisa', 'Brown', 'Retail Systems Ltd', 'CLIENT', NOW(), NOW()),

  -- Technician users
  (5, 'technician', 'louis.garcia@fixly.com', '$2a$10$TkLMl//9S4uJ.rWkhLeCSefvOVyg7MDxumJWh/ijrR8LCo8fbfXBe', 'Louis', 'Garcia', 'Fixly Corp', 'TECHNICIAN', NOW(), NOW()),
  (6, 'tech2', 'carlos.martinez@fixly.com', '$2a$10$TkLMl//9S4uJ.rWkhLeCSefvOVyg7MDxumJWh/ijrR8LCo8fbfXBe', 'Carlos', 'Martinez', 'Fixly Corp', 'TECHNICIAN', NOW(), NOW()),
  (7, 'tech3', 'emma.wilson@fixly.com', '$2a$10$TkLMl//9S4uJ.rWkhLeCSefvOVyg7MDxumJWh/ijrR8LCo8fbfXBe', 'Emma', 'Wilson', 'Fixly Corp', 'TECHNICIAN', NOW(), NOW()),
  (8, 'tech4', 'james.taylor@fixly.com', '$2a$10$TkLMl//9S4uJ.rWkhLeCSefvOVyg7MDxumJWh/ijrR8LCo8fbfXBe', 'James', 'Taylor', 'Fixly Corp', 'TECHNICIAN', NOW(), NOW()),

  -- Supervisor users
  (9, 'supervisor', 'martha.rodriguez@fixly.com', '$2a$10$A99XRsJgEk3a3iy6Fethrek9IWkEZZQkVb9WfVINvGdpIXv1SANiO', 'Martha', 'Rodriguez', 'Fixly Corp', 'SUPERVISOR', NOW(), NOW()),
  (10, 'supervisor2', 'robert.anderson@fixly.com', '$2a$10$A99XRsJgEk3a3iy6Fethrek9IWkEZZQkVb9WfVINvGdpIXv1SANiO', 'Robert', 'Anderson', 'Fixly Corp', 'SUPERVISOR', NOW(), NOW());

-- WorkOrders with different statuses and priorities
INSERT INTO workorder (id, identifier, title, description, status, priority, supervision_status, location, created_at, updated_at, created_by, supervised_by) VALUES
  -- PENDING work orders
  (1, 'WO-123456-082025', 'Air conditioning system repair', 'The air conditioning unit in the main office is not cooling properly. Temperature remains at 78°F despite being set to 68°F.', 'PENDING', 'HIGH', 'PENDING', 'Main Office Building A - Floor 2', NOW() - INTERVAL 2 DAY, NOW() - INTERVAL 2 DAY, 2, 9),
  (2, 'WO-234567-082025', 'Replace broken window', 'Large crack in conference room window needs immediate replacement for safety reasons.', 'PENDING', 'MEDIUM', 'PENDING', 'Conference Room 201 - Building B', NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 1 DAY, 3, 9),
  (3, 'WO-345678-082025', 'Server room ventilation check', 'Server room temperature monitoring shows irregular patterns. Requires urgent inspection.', 'PENDING', 'CRITICAL', 'PENDING', 'Server Room - Basement Level', NOW() - INTERVAL 3 HOUR, NOW() - INTERVAL 3 HOUR, 4, 10),

  -- ASSIGNED work orders
  (4, 'WO-456789-082025', 'Replace fluorescent light fixtures', 'Multiple fluorescent lights flickering in warehouse section C. Replace with LED alternatives.', 'ASSIGNED', 'MEDIUM', 'PENDING', 'Warehouse Section C - Rows 15-20', NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 6 HOUR, 2, 9),
  (5, 'WO-567890-082025', 'Repair loading dock mechanism', 'Loading dock door #3 is stuck in half-open position. Mechanism needs repair or replacement.', 'ASSIGNED', 'HIGH', 'PENDING', 'Loading Dock Area - Door #3', NOW() - INTERVAL 8 HOUR, NOW() - INTERVAL 4 HOUR, 3, 10),

  -- IN_PROGRESS work orders
  (6, 'WO-678901-082025', 'Industrial printer maintenance', 'Quarterly maintenance on industrial label printer. Clean, calibrate, and replace worn parts.', 'IN_PROGRESS', 'LOW', 'APPROVED', 'Production Floor - Station 12', NOW() - INTERVAL 2 DAY, NOW() - INTERVAL 2 HOUR, 4, 9),
  (7, 'WO-789012-082025', 'Emergency fire alarm system check', 'Fire alarm panel showing fault code. Complete system diagnostic required immediately.', 'IN_PROGRESS', 'CRITICAL', 'APPROVED', 'Security Office - Fire Control Panel', NOW() - INTERVAL 5 HOUR, NOW() - INTERVAL 1 HOUR, 2, 10),
  (8, 'WO-890123-082025', 'Repair office chairs', 'Five executive office chairs have broken hydraulic cylinders. Repair or replace cylinders.', 'IN_PROGRESS', 'LOW', 'APPROVED', 'Executive Offices - Floor 3', NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 30 MINUTE, 3, 9),

  -- READY_FOR_REVIEW work orders
  (9, 'WO-901234-082025', 'Install new security cameras', 'Install 4 new IP security cameras in parking lot area with night vision capability.', 'READY_FOR_REVIEW', 'MEDIUM', 'PENDING', 'Employee Parking Lot - North Side', NOW() - INTERVAL 3 DAY, NOW() - INTERVAL 1 HOUR, 4, 10),
  (10, 'WO-012345-082025', 'Plumbing leak repair', 'Water leak detected under sink in employee break room. Pipe joints need tightening or replacement.', 'READY_FOR_REVIEW', 'HIGH', 'CHANGES_REQUIRED', 'Employee Break Room - Building A', NOW() - INTERVAL 2 DAY, NOW() - INTERVAL 3 HOUR, 2, 9),
  (11, 'WO-123450-082025', 'HVAC filter replacement', 'Replace all HVAC filters in building C. Standard quarterly maintenance procedure.', 'READY_FOR_REVIEW', 'LOW', 'PENDING', 'Building C - All HVAC Units', NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 2 HOUR, 3, 10),

  -- CLOSED work orders
  (12, 'WO-234561-082025', 'Network cable replacement', 'Replace damaged ethernet cables in IT department. Cables were damaged during furniture relocation.', 'CLOSED', 'MEDIUM', 'APPROVED', 'IT Department - Workstations 1-8', NOW() - INTERVAL 5 DAY, NOW() - INTERVAL 1 DAY, 4, 9),
  (13, 'WO-345672-082025', 'Paint warehouse walls', 'Repaint warehouse walls in sections A and B. Previous paint is chipping and affecting safety compliance.', 'CLOSED', 'LOW', 'APPROVED', 'Warehouse Sections A & B', NOW() - INTERVAL 7 DAY, NOW() - INTERVAL 2 DAY, 2, 10),
  (14, 'WO-456783-082025', 'Replace broken door handle', 'Main entrance door handle is loose and needs replacement. Security concern for building access.', 'CLOSED', 'HIGH', 'APPROVED', 'Main Entrance - Building A', NOW() - INTERVAL 4 DAY, NOW() - INTERVAL 1 DAY, 3, 9);

-- Technician assignments to work orders
INSERT INTO workorder_assigned_to (workorder_id, user_id) VALUES
  -- ASSIGNED work orders
  (4, 5), -- Louis assigned to light fixtures
  (5, 6), -- Carlos assigned to loading dock

  -- IN_PROGRESS work orders
  (6, 7), -- Emma working on printer maintenance
  (7, 5), -- Louis working on fire alarm (CRITICAL)
  (8, 8), -- James working on office chairs

  -- READY_FOR_REVIEW work orders
  (9, 6),  -- Carlos completed security cameras
  (10, 7), -- Emma completed plumbing (needs changes)
  (11, 8), -- James completed HVAC filters

  -- CLOSED work orders
  (12, 5), -- Louis completed network cables
  (13, 6), -- Carlos completed warehouse painting
  (13, 7), -- Emma also worked on warehouse painting (team effort)
  (14, 8); -- James completed door handle

-- Comments for work orders (internal notes and client communications)
INSERT INTO comments (id, content, comment_type, created_at, author, workorder_id) VALUES
  -- Comments for PENDING work orders
  (1, 'Initial assessment completed. AC unit requires professional HVAC technician. Scheduling inspection for tomorrow morning.', 'INTERNAL', NOW() - INTERVAL 1 DAY, 9, 1),
  (2, 'Client reported temperature issues started 3 days ago. Possibly related to recent heat wave.', 'CLIENT_NOTE', NOW() - INTERVAL 1 DAY, 2, 1),
  (3, 'Window damage appears to be caused by thermal expansion. Recommend replacing with tempered glass.', 'INTERNAL', NOW() - INTERVAL 12 HOUR, 9, 2),

  -- Comments for ASSIGNED work orders
  (4, 'Technician dispatched to warehouse. Estimated completion time: 4 hours. LED fixtures ordered and in stock.', 'INTERNAL', NOW() - INTERVAL 4 HOUR, 9, 4),
  (5, 'Client requested work to be completed after 6 PM to avoid disrupting shipping operations.', 'CLIENT_NOTE', NOW() - INTERVAL 6 HOUR, 3, 5),
  (6, 'Loading dock mechanism parts ordered. Expected delivery tomorrow morning. Will complete repair by end of week.', 'INTERNAL', NOW() - INTERVAL 2 HOUR, 6, 5),

  -- Comments for IN_PROGRESS work orders
  (7, 'Maintenance 60% complete. Printer head cleaned and calibrated. Installing new ink cartridges next.', 'INTERNAL', NOW() - INTERVAL 1 HOUR, 7, 6),
  (8, 'URGENT: Fire alarm panel reset twice. Running full diagnostic now. May require manufacturer support.', 'INTERNAL', NOW() - INTERVAL 30 MINUTE, 5, 7),
  (9, 'Three chairs repaired successfully. Two require complete cylinder replacement. Parts on order.', 'INTERNAL', NOW() - INTERVAL 15 MINUTE, 8, 8),

  -- Comments for READY_FOR_REVIEW work orders
  (10, 'All 4 cameras installed and tested. Night vision functionality confirmed. System ready for final approval.', 'INTERNAL', NOW() - INTERVAL 30 MINUTE, 6, 9),
  (11, 'Leak repair completed but water pressure seems lower than expected. Recommending additional inspection.', 'INTERNAL', NOW() - INTERVAL 2 HOUR, 7, 10),
  (12, 'Please verify the plumbing work includes checking all adjacent connections for potential issues.', 'CLIENT_NOTE', NOW() - INTERVAL 1 HOUR, 2, 10),
  (13, 'All HVAC filters replaced. System performance improved. Next maintenance scheduled in 3 months.', 'INTERNAL', NOW() - INTERVAL 1 HOUR, 8, 11),

  -- Comments for CLOSED work orders
  (14, 'Network connectivity restored to all 8 workstations. Cable management improved. Issue resolved.', 'INTERNAL', NOW() - INTERVAL 1 DAY, 5, 12),
  (15, 'Warehouse painting completed on schedule. Used industrial-grade paint for durability. Excellent work quality.', 'CLIENT_NOTE', NOW() - INTERVAL 1 DAY, 4, 13),
  (16, 'Door handle replacement completed. New handle includes improved security features as requested.', 'INTERNAL', NOW() - INTERVAL 1 DAY, 8, 14);

-- Status history tracking work order state changes
INSERT INTO status_history (id, workorder_id, previous_status, new_status, previous_supervision_status, new_supervision_status, updated_by, updated_at) VALUES
  -- ASSIGNED work orders history
  (1, 4, 'PENDING', 'ASSIGNED', 'PENDING', 'PENDING', 9, NOW() - INTERVAL 6 HOUR),
  (2, 5, 'PENDING', 'ASSIGNED', 'PENDING', 'PENDING', 10, NOW() - INTERVAL 4 HOUR),

  -- IN_PROGRESS work orders history
  (3, 6, 'PENDING', 'ASSIGNED', 'PENDING', 'PENDING', 9, NOW() - INTERVAL 1 DAY),
  (4, 6, 'ASSIGNED', 'IN_PROGRESS', 'PENDING', 'APPROVED', 7, NOW() - INTERVAL 2 HOUR),
  (5, 7, 'PENDING', 'ASSIGNED', 'PENDING', 'PENDING', 10, NOW() - INTERVAL 3 HOUR),
  (6, 7, 'ASSIGNED', 'IN_PROGRESS', 'PENDING', 'APPROVED', 5, NOW() - INTERVAL 1 HOUR),
  (7, 8, 'PENDING', 'ASSIGNED', 'PENDING', 'PENDING', 9, NOW() - INTERVAL 8 HOUR),
  (8, 8, 'ASSIGNED', 'IN_PROGRESS', 'PENDING', 'APPROVED', 8, NOW() - INTERVAL 30 MINUTE),

  -- READY_FOR_REVIEW work orders history
  (9, 9, 'PENDING', 'ASSIGNED', 'PENDING', 'PENDING', 10, NOW() - INTERVAL 2 DAY),
  (10, 9, 'ASSIGNED', 'IN_PROGRESS', 'PENDING', 'APPROVED', 6, NOW() - INTERVAL 1 DAY),
  (11, 9, 'IN_PROGRESS', 'READY_FOR_REVIEW', 'APPROVED', 'PENDING', 6, NOW() - INTERVAL 1 HOUR),
  (12, 10, 'PENDING', 'ASSIGNED', 'PENDING', 'PENDING', 9, NOW() - INTERVAL 1 DAY),
  (13, 10, 'ASSIGNED', 'IN_PROGRESS', 'PENDING', 'APPROVED', 7, NOW() - INTERVAL 12 HOUR),
  (14, 10, 'IN_PROGRESS', 'READY_FOR_REVIEW', 'APPROVED', 'CHANGES_REQUIRED', 9, NOW() - INTERVAL 3 HOUR),
  (15, 11, 'PENDING', 'ASSIGNED', 'PENDING', 'PENDING', 10, NOW() - INTERVAL 12 HOUR),
  (16, 11, 'ASSIGNED', 'IN_PROGRESS', 'PENDING', 'APPROVED', 8, NOW() - INTERVAL 6 HOUR),
  (17, 11, 'IN_PROGRESS', 'READY_FOR_REVIEW', 'APPROVED', 'PENDING', 8, NOW() - INTERVAL 2 HOUR),

  -- CLOSED work orders history
  (18, 12, 'PENDING', 'ASSIGNED', 'PENDING', 'PENDING', 9, NOW() - INTERVAL 4 DAY),
  (19, 12, 'ASSIGNED', 'IN_PROGRESS', 'PENDING', 'APPROVED', 5, NOW() - INTERVAL 3 DAY),
  (20, 12, 'IN_PROGRESS', 'READY_FOR_REVIEW', 'APPROVED', 'APPROVED', 5, NOW() - INTERVAL 2 DAY),
  (21, 12, 'READY_FOR_REVIEW', 'CLOSED', 'APPROVED', 'APPROVED', 9, NOW() - INTERVAL 1 DAY),
  (22, 13, 'PENDING', 'ASSIGNED', 'PENDING', 'PENDING', 10, NOW() - INTERVAL 6 DAY),
  (23, 13, 'ASSIGNED', 'IN_PROGRESS', 'PENDING', 'APPROVED', 6, NOW() - INTERVAL 5 DAY),
  (24, 13, 'IN_PROGRESS', 'READY_FOR_REVIEW', 'APPROVED', 'APPROVED', 6, NOW() - INTERVAL 3 DAY),
  (25, 13, 'READY_FOR_REVIEW', 'CLOSED', 'APPROVED', 'APPROVED', 10, NOW() - INTERVAL 2 DAY),
  (26, 14, 'PENDING', 'ASSIGNED', 'PENDING', 'PENDING', 9, NOW() - INTERVAL 3 DAY),
  (27, 14, 'ASSIGNED', 'IN_PROGRESS', 'PENDING', 'APPROVED', 8, NOW() - INTERVAL 2 DAY),
  (28, 14, 'IN_PROGRESS', 'READY_FOR_REVIEW', 'APPROVED', 'APPROVED', 8, NOW() - INTERVAL 1 DAY),
  (29, 14, 'READY_FOR_REVIEW', 'CLOSED', 'APPROVED', 'APPROVED', 9, NOW() - INTERVAL 1 DAY);

-- Sample attachments (file references for work orders and comments)
INSERT INTO attachments (id, file_name, file_url, file_type, uploaded_by, workorder_id, comment_id) VALUES
  -- Work order attachments
  (1, 'ac_unit_photo.jpg', 'https://res.cloudinary.com/fixly/image/upload/v1234567890/ac_unit_photo.jpg', 'image/jpeg', 2, 1, NULL),
  (2, 'temperature_readings.pdf', 'https://res.cloudinary.com/fixly/image/upload/v1234567891/temperature_readings.pdf', 'application/pdf', 2, 1, NULL),
  (3, 'broken_window.jpg', 'https://res.cloudinary.com/fixly/image/upload/v1234567892/broken_window.jpg', 'image/jpeg', 3, 2, NULL),
  (4, 'server_room_temps.xlsx', 'https://res.cloudinary.com/fixly/image/upload/v1234567893/server_room_temps.xlsx', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', 4, 3, NULL),
  (5, 'warehouse_lights_before.jpg', 'https://res.cloudinary.com/fixly/image/upload/v1234567894/warehouse_lights_before.jpg', 'image/jpeg', 2, 4, NULL),
  (6, 'loading_dock_stuck.mp4', 'https://res.cloudinary.com/fixly/video/upload/v1234567895/loading_dock_stuck.mp4', 'video/mp4', 3, 5, NULL),

  -- Comment attachments
  (7, 'printer_maintenance_checklist.pdf', 'https://res.cloudinary.com/fixly/image/upload/v1234567896/printer_maintenance_checklist.pdf', 'application/pdf', 7, 6, 7),
  (8, 'fire_alarm_diagnostic.pdf', 'https://res.cloudinary.com/fixly/image/upload/v1234567897/fire_alarm_diagnostic.pdf', 'application/pdf', 5, 7, 8),
  (9, 'chair_repair_progress.jpg', 'https://res.cloudinary.com/fixly/image/upload/v1234567898/chair_repair_progress.jpg', 'image/jpeg', 8, 8, 9),
  (10, 'security_cameras_installed.jpg', 'https://res.cloudinary.com/fixly/image/upload/v1234567899/security_cameras_installed.jpg', 'image/jpeg', 6, 9, 10),
  (11, 'plumbing_repair_complete.jpg', 'https://res.cloudinary.com/fixly/image/upload/v1234567900/plumbing_repair_complete.jpg', 'image/jpeg', 7, 10, 11),
  (12, 'hvac_filters_replaced.jpg', 'https://res.cloudinary.com/fixly/image/upload/v1234567901/hvac_filters_replaced.jpg', 'image/jpeg', 8, 11, 13),
  (13, 'network_cables_completed.jpg', 'https://res.cloudinary.com/fixly/image/upload/v1234567902/network_cables_completed.jpg', 'image/jpeg', 5, 12, 14),
  (14, 'warehouse_painting_final.jpg', 'https://res.cloudinary.com/fixly/image/upload/v1234567903/warehouse_painting_final.jpg', 'image/jpeg', 6, 13, 15),
  (15, 'new_door_handle.jpg', 'https://res.cloudinary.com/fixly/image/upload/v1234567904/new_door_handle.jpg', 'image/jpeg', 8, 14, 16);