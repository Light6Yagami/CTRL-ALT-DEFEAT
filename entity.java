@Entity
@Table(name = "events")
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    private String name;
    
    private String description;
    
    private LocalDateTime date;
    
    private Integer capacity;
    
    private String location;
    
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications = new ArrayList<>();
    
    // constructors, getters, and setters
}

