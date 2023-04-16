@Service
@Transactional
public class EventService {
    
    private final EventRepository eventRepository;
    
    private final EmployeeRepository employeeRepository;
    
    public EventService(EventRepository eventRepository, EmployeeRepository employeeRepository) {
        this.eventRepository = eventRepository;
        this.employeeRepository = employeeRepository;
    }
    
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }
    
    public Event updateEvent(Long id, Event event) {
        Event existingEvent = eventRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
        existingEvent.setName(event.getName());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setDate(event.getDate());
        existingEvent.setCapacity(event.getCapacity());
        existingEvent.setLocation(event.getLocation());
        return eventRepository.save(existingEvent);
    }
    
    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
        eventRepository.delete(event);
    }
    
    public List<Event> getEvents() {
        return eventRepository.findAll();
    }
    
    public Application applyForEvent(Long eventId, Long employeeId) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));
        if (event.getApplications().size() >= event.getCapacity()) {
            throw new ApplicationException("Event is already full");
        }
        Application application = new Application(event, employee);
        event.getApplications().add(application);
        return application;
    }
    
    public void selectApplications(Long eventId, List<Long> selectedEmployeeIds) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow