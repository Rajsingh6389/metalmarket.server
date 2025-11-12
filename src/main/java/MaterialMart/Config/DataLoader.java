package MaterialMart.Config;

import MaterialMart.Model.Product;
import MaterialMart.Model.User;
import MaterialMart.Repository.ProductRepository;
import MaterialMart.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner load(ProductRepository productRepo, UserRepository userRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            if (productRepo.count() == 0) {
                String brass="https://i.ibb.co/xqQKfbQC/70f2674d7f39.jpg";
                String iron="https://i.ibb.co/pjKkKwdT/052d3c90d364.jpg";
                String copper="https://i.ibb.co/k2S2507L/0a2d7111658c.jpg";


                productRepo.save(new Product(null, "Brass", "Metal", 60.0, 100, "Brass is an alloy of copper and zinc (commonly), with good machinability, corrosion resistance, and a yellow-golden appearance. It’s used in fittings, hardware, musical instruments, decorative items, etc.", brass));
                productRepo.save(new Product(null, "Iron", "Metal", 4.0, 100, "Iron (commonly in forms such as mild steel, scrap iron, structural iron) is abundant, lower cost compared to non-ferrous metals, used in construction, manufacturing, etc.", iron));
                productRepo.save(new Product(null, "Copper", "Metal", 140.0, 100, "Copper is a reddish-brown metal, excellent conductor of electricity and heat, used in electrical wiring, plumbing, electronics, etc. Higher value than common ferrous metals.",copper ));

            }
            if (!userRepo.existsByEmail("MetalMarket92@gmail.com")) {
                User admin = new User();
                admin.setName("MetalMarket");
                admin.setEmail("MetalMarket92@gmail.com");
                admin.setPassword(new BCryptPasswordEncoder().encode("MetalMarket123"));
                admin.setRole("ADMIN");
                admin.setPhone("9201309578");
                userRepo.save(admin);
            }
        };
    }
}
