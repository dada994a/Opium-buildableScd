package we.devs.opium.event.impl;


import we.devs.opium.event.Event;
import net.minecraft.client.particle.Particle;
import net.minecraft.particle.ParticleEffect;

public class ParticleEvent extends Event {
    public static class AddParticle extends ParticleEvent {

        public final Particle particle;
        public AddParticle(Particle particle){
            this.particle = particle;
        }

    }

    public static class AddEmmiter extends ParticleEvent {
        public final ParticleEffect emmiter;

        public AddEmmiter(ParticleEffect emmiter) {
            this.emmiter = emmiter;
        }
    }
}